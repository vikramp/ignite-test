package org.jboss.as.quickstarts.cmt.cache;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Logger;


@ApplicationScoped
public class EntityManagerProducer implements PassivationCapable , Serializable {

    private static final Logger logger = Logger.getLogger(EntityManagerProducer.class.getCanonicalName());


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRED)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Produces
    public EntityManager getEntityManager(){
        logger.warning("Entity Manager injected");
        return entityManager;
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        logger.warning("Closing Entity Manager");
    }

    @Override
    public String getId() {
        return EntityManagerProducer.class.getCanonicalName() ;
    }
}
