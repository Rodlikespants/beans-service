package db.daos;

import db.entities.categories.CategoryEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class CategoriesDAO extends AbstractDAO<CategoryEntity> {

    public CategoriesDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<CategoryEntity> findById(long id) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<CategoryEntity> cr = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = cr.from(CategoryEntity.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        Query<CategoryEntity> query = this.currentSession().createQuery(cr);
        List<CategoryEntity> results = query.getResultList();
        return results.stream().findFirst();
    }

    public Optional<CategoryEntity> findByName(String name) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<CategoryEntity> cr = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = cr.from(CategoryEntity.class);
        cr.select(root).where(cb.equal(root.get("name"), name));

        Query<CategoryEntity> query = this.currentSession().createQuery(cr);
        List<CategoryEntity> results = query.getResultList();
        return results.stream().findFirst();
    }


    public List<CategoryEntity> findAll() {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<CategoryEntity> cr = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = cr.from(CategoryEntity.class);
        cr.select(root);

        Query<CategoryEntity> query = this.currentSession().createQuery(cr);
        List<CategoryEntity> results = query.getResultList();
        return results;
    }

    public CategoryEntity save(CategoryEntity categoryEntity) {
        return this.persist(categoryEntity);
    }
}
