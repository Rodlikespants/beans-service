package db.mappers;

import db.entities.categories.CategoryEntity;
import models.categories.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.isActive(),
                ParentCategoryMapper.toParentCategory(entity.getParentCategory())
        );
    }
}
