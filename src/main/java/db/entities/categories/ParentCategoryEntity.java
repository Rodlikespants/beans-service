package db.entities.categories;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

/*
CREATE TABLE parent_categories (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    UNIQUE (name)
);
 */

@Entity
@Table(name = "parent_categories")
public class ParentCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "active")
    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentCategory")
//    @OneToMany(targetEntity = CategoryEntity.class)
    private List<CategoryEntity> categories;

    public ParentCategoryEntity() {}

    public ParentCategoryEntity(String name) {
        this.name = name;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentCategoryEntity that = (ParentCategoryEntity) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive);
    }

    @Override
    public String toString() {
        return "ParentCategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
