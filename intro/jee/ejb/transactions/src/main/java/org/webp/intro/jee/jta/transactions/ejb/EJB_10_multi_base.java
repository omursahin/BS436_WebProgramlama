package org.webp.intro.jee.jta.transactions.ejb;

import org.webp.intro.jee.jta.transactions.data.Foo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class EJB_10_multi_base {


    @PersistenceContext
    private EntityManager em;


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean isPresentByCheckingOnNewTransaction(String name){
        return em.find(Foo.class, name) != null;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addInNewTransaction(String name){
        Foo foo = new Foo(name);
        em.persist(foo);
    }


    public void add(String name){
        Foo foo = new Foo(name);
        em.persist(foo);
    }


}
