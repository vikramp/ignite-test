package org.jboss.as.quickstarts.cmt.cache;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicyFactory;
import org.apache.ignite.cache.jta.jndi.CacheJndiTmFactory;
import org.apache.ignite.cache.jta.jndi.CacheJndiTmLookup;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.H2Dialect;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;
import org.jboss.as.quickstarts.cmt.model.OnboardingProcess;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.TransactionManager;
import java.io.Serializable;
import java.sql.Types;
import java.util.Arrays;
import java.util.logging.Logger;

@Singleton
@Startup
public class CacheInitializer implements Serializable {


  private static final Logger logger = Logger.getLogger(CacheInitializer.class.getCanonicalName());
  public static final String ONBOARDING_CACHE = "ONBOARDING_CACHE";
  public static Ignite igniteHandle;
  public static IgniteCache<String, OnboardingProcess> onboardingCache;

  @Inject
  private OnboardingProcessCacheStoreFactory onboardingProcessCacheStoreFactory ;

  @Inject
  private OnboardingProcessCacheSessionListenerFactory listenerFactory;


  @PostConstruct
  public void init(){
    logger.info("Initializing Ignite");
    TcpCommunicationSpi tcpCommunicationSpi = new TcpCommunicationSpi();
    TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder().setAddresses(Arrays.asList("127.0.0.1"));
    TcpDiscoverySpi spi = new TcpDiscoverySpi().setIpFinder(ipFinder);

    DataStorageConfiguration dsStorageConfigureation = new DataStorageConfiguration();
    DataRegionConfiguration defaultdsRegionConfig = new DataRegionConfiguration();
    defaultdsRegionConfig = defaultdsRegionConfig.setPersistenceEnabled(Boolean.FALSE).setMetricsEnabled(Boolean.TRUE);
    DataRegionConfiguration masterRegionConfiguration = new DataRegionConfiguration().setName("MasterDataRegion").setMetricsEnabled(Boolean.TRUE);
    dsStorageConfigureation.setWalPath("node-data/wal").setWalArchivePath("node-data/archivepath").setDefaultDataRegionConfiguration(defaultdsRegionConfig)
            .setDataRegionConfigurations(masterRegionConfiguration);

    NearCacheConfiguration<String, OnboardingProcess> nearCacheConfiguration = new NearCacheConfiguration<String,OnboardingProcess>();
    LruEvictionPolicyFactory lruEvictionPolicyFactory = new LruEvictionPolicyFactory();
    lruEvictionPolicyFactory.setMaxSize(200000);
    nearCacheConfiguration.setNearEvictionPolicyFactory(lruEvictionPolicyFactory);

    CacheConfiguration<String, OnboardingProcess> onboardingProcessCacheConfiguration = new CacheConfiguration<String,OnboardingProcess>();


    JdbcType onboardingProcessEntity = new JdbcType();
    onboardingProcessEntity.setCacheName(CacheInitializer.ONBOARDING_CACHE);
    onboardingProcessEntity.setKeyType(String.class);
    onboardingProcessEntity.setValueType(OnboardingProcess.class);

    onboardingProcessEntity.setDatabaseTable("OnBoardingProcess");

    onboardingProcessEntity.setKeyFields(new JdbcTypeField(Types.VARCHAR, "CUSTOMERNAME", String.class, "customerName"));
    onboardingProcessEntity.setValueFields(new JdbcTypeField(java.sql.Types.VARCHAR, "STATUS", String.class, "status"));



    onboardingProcessCacheConfiguration = onboardingProcessCacheConfiguration
            .setDataRegionName("MasterDataRegion")
            .setName(CacheInitializer.ONBOARDING_CACHE)
            .setCacheMode(CacheMode.REPLICATED)
            .setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL)
            .setReadThrough(Boolean.TRUE)
            .setWriteThrough(Boolean.TRUE)
            .setNearConfiguration(nearCacheConfiguration)
            .setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC)
            .setCacheStoreFactory(onboardingProcessCacheStoreFactory)
            .setWriteBehindEnabled(Boolean.FALSE)
           // .setCacheStoreSessionListenerFactories(listenerFactory)
            .setTransactionManagerLookupClassName(CacheJndiTmLookup.class.getCanonicalName());

    IgniteConfiguration cfg = new IgniteConfiguration().setPeerClassLoadingEnabled(true).setClientMode(Boolean.FALSE)
            .setIgniteHome("/Users/vip/workspace/local-ignite-home")
            .setDeploymentMode(DeploymentMode.CONTINUOUS)
            .setMetricsLogFrequency(60*10*1000).setCommunicationSpi(tcpCommunicationSpi).setDiscoverySpi(spi)
            .setDataStorageConfiguration(dsStorageConfigureation)
          //  .setAuthenticationEnabled(Boolean.TRUE)
            .setClientMode(Boolean.FALSE)
            .setTransactionConfiguration(
                    new TransactionConfiguration()
                            //.setUseJtaSynchronization(Boolean.TRUE)
                            .setTxManagerFactory(new CacheJndiTmFactory("java:jboss/TransactionManager"))
                            .setDefaultTxConcurrency(TransactionConcurrency.OPTIMISTIC)
                            .setDefaultTxIsolation(TransactionIsolation.READ_COMMITTED))
            .setCacheConfiguration(onboardingProcessCacheConfiguration)
            .setCacheStoreSessionListenerFactories(listenerFactory);






    Ignite ignite = Ignition.getOrStart(cfg);
    logger.info("Initialized Ignite Cache, triggering acivation the cluster");
    ignite.active();
    this.igniteHandle = ignite;
    logger.info("Cluster activated");
    IgniteCache<String,OnboardingProcess> onboardingCache = ignite.getOrCreateCache(CacheInitializer.ONBOARDING_CACHE);
    if(onboardingCache == null)
      throw new RuntimeException("Issue in setting onboarding cache");
    else
      this.onboardingCache = onboardingCache;
    logger.info("cache handle set");

  }
}
