package org.jboss.as.quickstarts.cmt.cache;

import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.jboss.as.quickstarts.cmt.model.OnboardingProcess;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

@Transactional(Transactional.TxType.REQUIRED)
@Named
public class EntityManagerCacheStore implements CacheStore<String, OnboardingProcess> {

    private static final  Logger logger = Logger.getLogger(EntityManagerCacheStore.class.getCanonicalName());

    CacheStoreSession cacheSession;

    @CacheStoreSessionResource
    public void setCacheSession(CacheStoreSession cacheSession){
        this.cacheSession = cacheSession;
    }

    @Override
    public void loadCache(IgniteBiInClosure<String, OnboardingProcess> clo, @Nullable Object... args) throws CacheLoaderException {

    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {

    }

    @Override
    public OnboardingProcess load(String s) throws CacheLoaderException {
        return null;
    }

    @Override
    public Map<String, OnboardingProcess> loadAll(Iterable<? extends String> iterable) throws CacheLoaderException {
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends String, ? extends OnboardingProcess> entry) throws CacheWriterException {
        if(this.cacheSession == null)
            throw new RuntimeException("Cache Session is null");

        if(this.cacheSession.attachment() == null)
           throw new RuntimeException("EntityManager not set");

        logger.warning("Entity Manager found");
        EntityManager em = this.cacheSession.attachment();
      /*  em.persist(entry.getValue());
        em.flush();*/
        logger.warning("Entity Manager persist and flush called");
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends String, ? extends OnboardingProcess>> collection) throws CacheWriterException {

    }

    @Override
    public void delete(Object o) throws CacheWriterException {

    }

    @Override
    public void deleteAll(Collection<?> collection) throws CacheWriterException {

    }
}
