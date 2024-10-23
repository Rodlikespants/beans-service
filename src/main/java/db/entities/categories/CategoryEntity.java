package db.entities.categories;

import jakarta.persistence.*;
import java.util.Objects;

/*
CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    parent_category_id BIGINT,
    UNIQUE (name)
);
ALTER TABLE categories ADD CONSTRAINT fk_parent_category_id FOREIGN KEY (parent_category_id) REFERENCES parent_categories(id);
 */

@Entity
@Table(name = "categories")
public class CategoryEntity {
    public static final String NONE_CATEGORY = "NONE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_category_id")
    private ParentCategoryEntity parentCategory;

    @Column(name = "active")
    private boolean isActive;

    public CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
        this.isActive = true;
    }

    public CategoryEntity(Long id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public CategoryEntity(Long id, String name, boolean isActive, ParentCategoryEntity parentCategory) {
        this(id, name, isActive);
        this.parentCategory = parentCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ParentCategoryEntity getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ParentCategoryEntity parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(parentCategory, that.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentCategory, isActive);
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                ", isActive=" + isActive +
                '}';
    }
}
