package db;

import db.entities.OrderEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<OrderEntity> {
    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
//        sessionFactory.openSession();
    }

    public Optional<OrderEntity> findById(long id) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<OrderEntity> cr = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> root = cr.from(OrderEntity.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        Query<OrderEntity> query = this.currentSession().createQuery(cr);
        List<OrderEntity> results = query.getResultList();
        return results.stream().findFirst();
    }

    public Optional<OrderEntity> findBySource(String source) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<OrderEntity> cr = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> root = cr.from(OrderEntity.class);
        cr.select(root).where(cb.equal(root.get("source"), source));

        Query<OrderEntity> query = this.currentSession().createQuery(cr);
        List<OrderEntity> results = query.getResultList();
        return results.stream().findFirst();
    }

    public List<OrderEntity> findAll() {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<OrderEntity> cr = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> root = cr.from(OrderEntity.class);
        cr.select(root);

        Query<OrderEntity> query = this.currentSession().createQuery(cr);
        List<OrderEntity> results = query.getResultList();
        return results;
    }
}
