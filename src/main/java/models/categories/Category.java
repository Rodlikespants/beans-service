package models.categories;

import java.util.Objects;

public class Category {
    private Long id;
    private String name;
    private ParentCategory parentCategory;
    private boolean isActive;

    public Category(Long id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public Category(Long id, String name, boolean isActive, ParentCategory parentCategory) {
        this(id, name, isActive);
        this.parentCategory = parentCategory;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ParentCategory getParentCategory() {
        return parentCategory;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return isActive == category.isActive && Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(parentCategory, category.parentCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentCategory, isActive);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                ", isActive=" + isActive +
                '}';
    }
}
