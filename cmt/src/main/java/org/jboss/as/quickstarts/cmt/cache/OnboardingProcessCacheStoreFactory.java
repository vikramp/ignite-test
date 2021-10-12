package org.jboss.as.quickstarts.cmt.cache;


import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStore;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.jboss.as.quickstarts.cmt.model.OnboardingProcess;
import javax.cache.Cache;
import javax.cache.integration.CacheWriterException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;


@Named
public class OnboardingProcessCacheStoreFactory extends CacheJdbcPojoStoreFactory<String,OnboardingProcess> {

    @PersistenceContext
    EntityManager entityManager;



    private static final Logger logger = Logger.getLogger(OnboardingProcessCacheStoreFactory.class.getCanonicalName());

    @Override
    public CacheJdbcPojoStore<String,OnboardingProcess> create() {
        DataSource ds = (DataSource) entityManager.getEntityManagerFactory().getProperties().get("javax.persistence.jtaDataSource");
        CacheJdbcPojoStore<String,OnboardingProcess> store = new CacheJdbcPojoStore<String,OnboardingProcess>(){


            @Override
            protected Connection openConnection(boolean autocommit) throws SQLException {
                return this.dataSrc.getConnection();
            }

            @Override
            public void sessionEnd(boolean commit) throws CacheWriterException {
                // Do nothing
            }

            @Override
            public void write(Cache.Entry entry) throws CacheWriterException {
                logger.info("About to write entry");
                super.write(entry);
                logger.info("Entry written by the JDBC store adapter");
            }

            @Override
            public void writeAll(Collection<Cache.Entry<? extends String, ? extends OnboardingProcess>> entries) throws CacheWriterException {
                logger.info("About to write entries");
                super.writeAll(entries);
                logger.info("Entries written by the JDBC store adapter");
            }
        };
        store.setDataSource(ds);
        store.setTypes(this.getTypes());
        return  store;
    }

}
