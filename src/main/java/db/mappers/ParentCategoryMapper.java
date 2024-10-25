package db.mappers;

import db.entities.categories.ParentCategoryEntity;
import models.categories.ParentCategory;

public class ParentCategoryMapper {
    public static ParentCategory toParentCategory(ParentCategoryEntity entity) {
        return new ParentCategory(
                entity.getId(),
                entity.getName(),
                entity.isActive()
        );
    }
}
