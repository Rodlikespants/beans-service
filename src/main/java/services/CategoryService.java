package services;

import db.daos.categories.CategoryDAO;
import db.daos.categories.ParentCategoryDAO;
import db.entities.categories.CategoryEntity;
import db.entities.categories.ParentCategoryEntity;
import db.mappers.CategoryMapper;
import models.categories.Category;
import models.categories.ParentCategory;

import javax.inject.Inject;
import java.util.Optional;

public class CategoryService {
    final CategoryDAO categoryDao;
    final ParentCategoryDAO parentCategoryDao;


    @Inject
    public CategoryService(CategoryDAO categoryDao, ParentCategoryDAO parentCategoryDao) {
        this.categoryDao = categoryDao;
        this.parentCategoryDao = parentCategoryDao;
    }

    public Optional<Category> findById(long id) {
        Optional<CategoryEntity> categoryEntity = categoryDao.findById(id);
        return categoryEntity.flatMap(it -> Optional.of(CategoryMapper.toCategory(it)));
    }

    public Optional<Category> findByName(String name) {
        Optional<CategoryEntity> categoryEntity = categoryDao.findByName(name);
        return categoryEntity.flatMap(it -> Optional.of(CategoryMapper.toCategory(it)));
    }
}
