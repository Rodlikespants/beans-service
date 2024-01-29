package db.daos;

import db.entities.UserEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<UserEntity> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<UserEntity> findById(long id) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = cr.from(UserEntity.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        Query<UserEntity> query = this.currentSession().createQuery(cr);
        List<UserEntity> results = query.getResultList();
        return results.stream().findFirst();
    }

    public List<UserEntity> findByUserId(long userId) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = cr.from(UserEntity.class);
        cr.select(root).where(cb.equal(root.get("userId"), userId));

        Query<UserEntity> query = this.currentSession().createQuery(cr);
        List<UserEntity> results = query.getResultList();
        return results;
    }

    public List<UserEntity> findAll() {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<UserEntity> cr = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = cr.from(UserEntity.class);
        cr.select(root);

        Query<UserEntity> query = this.currentSession().createQuery(cr);
        List<UserEntity> results = query.getResultList();
        return results;
    }
}