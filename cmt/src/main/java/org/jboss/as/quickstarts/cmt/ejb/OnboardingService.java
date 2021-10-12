package org.jboss.as.quickstarts.cmt.ejb;

import org.jboss.as.quickstarts.cmt.cache.CacheInitializer;
import org.jboss.as.quickstarts.cmt.model.OnboardingProcess;
import org.jboss.as.quickstarts.cmt.model.OnboardingState;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSException;
import java.rmi.RemoteException;

@Stateless
public class OnboardingService {


    @Inject
    CacheInitializer cacheInitializer;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void initiateOnboarding(String name) throws RemoteException, JMSException {

        OnboardingProcess onboardingProcess = new OnboardingProcess();
        onboardingProcess.setCustomerName(name);
        onboardingProcess.setStatus(OnboardingState.NEW);
        if(cacheInitializer == null)
             throw new RuntimeException("Cache Initializer is not set");
        if(cacheInitializer.onboardingCache == null)
            throw new RuntimeException("Onboarding cache handle not set properly");

        cacheInitializer.onboardingCache.put(name,onboardingProcess);
    }

}
