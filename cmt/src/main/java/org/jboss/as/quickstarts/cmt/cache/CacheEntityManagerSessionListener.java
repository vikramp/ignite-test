package org.jboss.as.quickstarts.cmt.cache;

import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cache.store.CacheStoreSessionListener;
import org.apache.ignite.lifecycle.LifecycleAware;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@Named
public class CacheEntityManagerSessionListener implements CacheStoreSessionListener, LifecycleAware {

    private static final Logger logger = Logger.getLogger(CacheEntityManagerSessionListener.class.getCanonicalName());

    private Provider<EntityManager> entityManagerProvider;

    @Inject
    public void setEntityManagerProvider(Provider<EntityManager> entityManagerProvider){
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void onSessionStart(CacheStoreSession ses) {
        if(ses.attachment() == null) {
             logger.warning("About to attach new Entity Manager ");
             EntityManager entityManager = this.entityManagerProvider.get();
             ses.attach(entityManager);
             logger.warning("Attached new Entity Manager ");
        }
    }

    @Override
    public void onSessionEnd(CacheStoreSession ses, boolean commit) {
        ses.attach(null);
        logger.warning("Dissociating entity manager");
    }

    @Override
    public void start() throws IgniteException {

    }

    @Override
    public void stop() throws IgniteException {

    }
}
