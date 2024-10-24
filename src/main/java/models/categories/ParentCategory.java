package models.categories;

import java.util.Objects;

public class ParentCategory {
    private Long id;
    private String name;
    private boolean isActive;
    public ParentCategory() {}

    public ParentCategory(String name) {
        this.name = name;
        this.isActive = true;
    }

    public ParentCategory(Long id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentCategory that = (ParentCategory) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive);
    }

    @Override
    public String toString() {
        return "ParentCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
