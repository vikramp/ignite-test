/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.cmt.ejb;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.apache.ignite.transactions.Transaction;
import org.jboss.as.quickstarts.cmt.cache.CacheInitializer;
import org.jboss.as.quickstarts.cmt.model.Customer;

@Stateless
public class CustomerManagerEJB {

    private static final Logger logger = Logger.getLogger(CustomerManagerEJB.class.getCanonicalName());

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private InvoiceManagerEJB invoiceManager;

    @Inject
    private OnboardingService onboardingService;



    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createCustomer(String name) throws RemoteException, JMSException {
        logger.warning("Request to add Customer with name :" + name + " : arrived");
        // see if cache is initialized
        CacheInitializer.onboardingCache.get(name);
        onboardingService.initiateOnboarding(name);

        Customer c1 = new Customer();
        c1.setName(name);
        entityManager.persist(c1);
        entityManager.flush();
        onboardingService.markOnboaringWIP(name);
      //  invoiceManager.createInvoice(name);

        logger.warning("Request to add Customer with name :" + name + " : processed");

     }

    /**
     * List all the customers.
     * 
     * @return
     * @throws NamingException
     * @throws NotSupportedException
     * @throws SystemException
     * @throws SecurityException
     * @throws IllegalStateException
     * @throws RollbackException
     * @throws HeuristicMixedException
     * @throws HeuristicRollbackException
     */
    @TransactionAttribute(TransactionAttributeType.NEVER)
    @SuppressWarnings("unchecked")
    public List<Customer> listCustomers() {
        return entityManager.createQuery("select c from Customer c").getResultList();
    }
}
