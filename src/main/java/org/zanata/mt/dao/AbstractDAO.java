package org.zanata.mt.dao;

import java.io.Serializable;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public abstract class AbstractDAO<T> implements Serializable {
    @PersistenceContext
    private EntityManager entityManager;

    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    EntityManager getEntityManager() {
        return entityManager;
    }

    @TransactionAttribute
    public T persist(T entity) {
        return getEntityManager().merge(entity);
    }

    @TransactionAttribute
    public void flush() {
        getEntityManager().flush();
    }
}