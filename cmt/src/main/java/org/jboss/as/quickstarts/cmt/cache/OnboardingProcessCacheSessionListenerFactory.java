package org.jboss.as.quickstarts.cmt.cache;


import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.jta.jndi.CacheJndiTmFactory;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cache.store.CacheStoreSessionListener;
import org.apache.ignite.cache.store.jdbc.CacheJdbcStoreSessionListener;

import javax.annotation.Resource;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheWriterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.sql.XAConnection;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.sql.SQLException;
import java.util.logging.Logger;

@Named
public class OnboardingProcessCacheSessionListenerFactory implements Factory<CacheStoreSessionListener> {

    private static final Logger logger = Logger.getLogger(OnboardingProcessCacheStoreFactory.class.getCanonicalName());


    Provider<EntityManager> entityManagerProvider;

    @Inject
    public void setEntityManagerProvider(Provider<EntityManager> provider){
        this.entityManagerProvider = provider;
    }

    @Override
    public CacheStoreSessionListener create() {
        logger.info("setting up jdbc session listener");
        CacheEntityManagerSessionListener listener =  new CacheEntityManagerSessionListener();
        listener.setEntityManagerProvider(this.entityManagerProvider);
        logger.info("jdbc session listener seeded");
        return listener;
    }
}
