package org.jboss.as.quickstarts.cmt.cache;


import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStore;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.jboss.as.quickstarts.cmt.model.OnboardingProcess;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheWriterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;


@Named
public class OnboardingProcessCacheStoreFactory implements Factory<CacheStore<String,OnboardingProcess>> {


    private static final Logger logger = Logger.getLogger(OnboardingProcessCacheStoreFactory.class.getCanonicalName());

    private Provider<EntityManagerCacheStore> providerBean ;

    @Inject
    public OnboardingProcessCacheStoreFactory(Provider<EntityManagerCacheStore> entityManagerCacheStoreProvider){
        this.providerBean = entityManagerCacheStoreProvider;
    }

    @Override
    public CacheStore<String, OnboardingProcess> create() {
        return providerBean.get();
    }
}
