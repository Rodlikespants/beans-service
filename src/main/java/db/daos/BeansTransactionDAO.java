package db.daos;

import db.entities.transactions.BeansTransactionEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.Date;
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

    /**
     * Attempts to avoid duplicate transactions checking hash of fields for a uniqueness key
     * @param hashText
     * @return
     */
    public Optional<BeansTransactionEntity> findByHashText(long userId, String hashText) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<BeansTransactionEntity> cr = cb.createQuery(BeansTransactionEntity.class);
        Root<BeansTransactionEntity> root = cr.from(BeansTransactionEntity.class);
        cr.select(root).where(cb.equal(root.get("hashtext"), hashText), cb.equal(root.get("userId"), userId));

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

    public List<BeansTransactionEntity> findUserTransactions(long userId, LocalDate start, LocalDate end, BeansTransactionEntity.Source source) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<BeansTransactionEntity> cr = cb.createQuery(BeansTransactionEntity.class);
        Root<BeansTransactionEntity> root = cr.from(BeansTransactionEntity.class);
        cr.select(root).where(
                cb.equal(root.get("userId"), userId),
                cb.lessThanOrEqualTo(root.get("effectiveDate").as(LocalDate.class), end),
                cb.greaterThanOrEqualTo(root.get("effectiveDate").as(LocalDate.class), start),
                cb.equal(root.get("source"), source)
        );

        Query<BeansTransactionEntity> query = this.currentSession().createQuery(cr);
        return query.getResultList();
    }

    public BeansTransactionEntity saveUnique(BeansTransactionEntity beansTxnEntity) {
        return findByHashText(beansTxnEntity.getUserId(), beansTxnEntity.getHashtext())
                .orElseGet(() -> save(beansTxnEntity));
    }

    public BeansTransactionEntity save(BeansTransactionEntity beansTxnEntity) {
        return this.persist(beansTxnEntity);
//        this.currentSession().save(beansTxnEntity);
    }
}
