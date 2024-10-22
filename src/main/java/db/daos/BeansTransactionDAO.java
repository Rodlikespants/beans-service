package db.daos;

import db.entities.transactions.BeansTransactionEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import javax.inject.Singleton;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class BeansTransactionDAO extends AbstractDAO<BeansTransactionEntity> {
    public BeansTransactionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<BeansTransactionEntity> findById(long id) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<BeansTransactionEntity> cr = cb.createQuery(BeansTransactionEntity.class);
        Root<BeansTransactionEntity> root = cr.from(BeansTransactionEntity.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        Query<BeansTransactionEntity> query = this.currentSession().createQuery(cr);
        List<BeansTransactionEntity> results = query.getResultList();
        return results.stream().findFirst();
    }

//    public List<BeansTransactionEntity> findByUserId() {
//        // Create CriteriaBuilder
//        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
//        // Create CriteriaQuery
//        CriteriaQuery<BeansTransactionEntity> cr = cb.createQuery(BeansTransactionEntity.class);
//        Root<BeansTransactionEntity> root = cr.from(BeansTransactionEntity.class);
//        cr.select(root);
//
//        Query<BeansTransactionEntity> query = this.currentSession().createQuery(cr);
//        List<BeansTransactionEntity> results = query.getResultList();
//        return results;
//    }

    public List<BeansTransactionEntity> findAll() {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<BeansTransactionEntity> cr = cb.createQuery(BeansTransactionEntity.class);
        Root<BeansTransactionEntity> root = cr.from(BeansTransactionEntity.class);
        cr.select(root);

        Query<BeansTransactionEntity> query = this.currentSession().createQuery(cr);
        List<BeansTransactionEntity> results = query.getResultList();
        return results;
    }

    public void save(BeansTransactionEntity beansTxnEntity) {
        this.persist(beansTxnEntity);
//        this.currentSession().save(beansTxnEntity);
    }
}
