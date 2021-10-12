package org.jboss.as.quickstarts.cmt.cache;


import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.cache.store.jdbc.CacheJdbcStoreSessionListener;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheWriterException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Named
public class OnboardingProcessCacheSessionListenerFactory implements Factory<CacheJdbcStoreSessionListener> {

    private static final Logger logger = Logger.getLogger(OnboardingProcessCacheStoreFactory.class.getCanonicalName());
    @PersistenceContext
    EntityManager em;

    @Override
    public CacheJdbcStoreSessionListener create() {
        logger.info("setting up jdbc session listener");
        DataSource ds = (DataSource) em.getEntityManagerFactory().getProperties().get("javax.persistence.jtaDataSource");
        CacheJdbcStoreSessionListener listener = new CacheJdbcStoreSessionListener(){
            @Override
            public void onSessionStart(CacheStoreSession ses) {
                logger.info("Listener session starts here");
                if (ses.attachment() == null) {
                    try {
                        Connection conn = this.getDataSource().getConnection();
                        ses.attach(conn);
                    }
                    catch (SQLException e) {
                        throw new CacheWriterException("Failed to start store session [tx=" + ses.transaction() + ']', e);
                    }
                }
            }

            @Override
            public void onSessionEnd(CacheStoreSession ses, boolean commit) {
                Connection conn = ses.attach(null);
                logger.info("Do nothing when session ends");
            }
        };
        listener.setDataSource(ds);
        logger.info("jdbc session listener seeded");
        return listener;
    }
}
