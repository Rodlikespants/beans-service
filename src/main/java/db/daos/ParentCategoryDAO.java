package db.daos;

import db.entities.categories.CategoryEntity;
import db.entities.categories.ParentCategoryEntity;
import io.dropwizard.hibernate.AbstractDAO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ParentCategoryDAO extends AbstractDAO<ParentCategoryEntity> {
    public ParentCategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<ParentCategoryEntity> findById(long id) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<ParentCategoryEntity> cr = cb.createQuery(ParentCategoryEntity.class);
        Root<ParentCategoryEntity> root = cr.from(ParentCategoryEntity.class);
        cr.select(root).where(cb.equal(root.get("id"), id));

        Query<ParentCategoryEntity> query = this.currentSession().createQuery(cr);
        List<ParentCategoryEntity> results = query.getResultList();
        return results.stream().findFirst();
    }

    public Optional<ParentCategoryEntity> findByName(String name) {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<ParentCategoryEntity> cr = cb.createQuery(ParentCategoryEntity.class);
        Root<ParentCategoryEntity> root = cr.from(ParentCategoryEntity.class);
        cr.select(root).where(cb.equal(root.get("name"), name));

        Query<ParentCategoryEntity> query = this.currentSession().createQuery(cr);
        List<ParentCategoryEntity> results = query.getResultList();
        return results.stream().findFirst();
    }


    public List<ParentCategoryEntity> findAll() {
        // Create CriteriaBuilder
        CriteriaBuilder cb = this.currentSession().getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<ParentCategoryEntity> cr = cb.createQuery(ParentCategoryEntity.class);
        Root<ParentCategoryEntity> root = cr.from(ParentCategoryEntity.class);
        cr.select(root);

        Query<ParentCategoryEntity> query = this.currentSession().createQuery(cr);
        List<ParentCategoryEntity> results = query.getResultList();
        return results;
    }

    public ParentCategoryEntity addParentCategory(String categoryName) {
        String sanitizedName = (categoryName.isBlank() || categoryName.isEmpty()) ? CategoryEntity.NONE_CATEGORY : categoryName;
        return findByName(sanitizedName).orElseGet(() -> save(new ParentCategoryEntity(sanitizedName)));
    }

    public ParentCategoryEntity save(ParentCategoryEntity categoryEntity) {
        return this.persist(categoryEntity);
    }
}
