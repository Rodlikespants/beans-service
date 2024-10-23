package db.daos;

import db.entities.categories.CategoryEntity;
import db.entities.categories.ParentCategoryEntity;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ParentCategoriesDAOTest {
    public DAOTestExtension database = DAOTestExtension.newBuilder()
            .addEntityClass(ParentCategoryEntity.class)
            .addEntityClass(CategoryEntity.class)
            .build();
    private ParentCategoriesDAO parentCategoriesDao;
    private CategoriesDAO categoriesDao;

    @BeforeEach
    public void setUp() {
        parentCategoriesDao = new ParentCategoriesDAO(database.getSessionFactory());
        categoriesDao       = new CategoriesDAO(database.getSessionFactory());
    }

    @Test
    public void testSaveParentCategory() {
        String categoryName = "Groceries";
        ParentCategoryEntity savedEntity = database.inTransaction(() -> parentCategoriesDao.addParentCategory(categoryName));

        List<ParentCategoryEntity> allCategories = database.inTransaction(() -> parentCategoriesDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(categoryName, allCategories.get(0).getName());

        ParentCategoryEntity actualEntity = database.inTransaction(() -> parentCategoriesDao.findByName(categoryName)).orElse(null);
        Assertions.assertNotNull(actualEntity);
        Assertions.assertEquals(categoryName, actualEntity.getName());
    }


    /**
     * Confirms that we cannot write two categories of the same name
     */
    @Test
    public void testSaveIfDoesNotExist() {
        String categoryName = "Groceries";
        ParentCategoryEntity categoryEntity1 = new ParentCategoryEntity(categoryName);
        ParentCategoryEntity savedEntity1 = database.inTransaction(() -> parentCategoriesDao.save(categoryEntity1));

        // Attempt to save a category with the same name
        ParentCategoryEntity categoryEntity2 = new ParentCategoryEntity(categoryName);
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () ->{
            database.inTransaction(() -> parentCategoriesDao.save(categoryEntity2));
        });
        Assertions.assertEquals("could not execute statement", e.getMessage());
    }

    @Test
    public void testSaveIfAbsent() {
        String categoryName = "Groceries";
        ParentCategoryEntity savedEntity1 = database.inTransaction(() -> parentCategoriesDao.addParentCategory(categoryName));

        // Attempt to save a category with the same name
        ParentCategoryEntity savedEntity2 = database.inTransaction(() -> parentCategoriesDao.addParentCategory(categoryName));
        Assertions.assertEquals(savedEntity1, savedEntity2);
    }

    @Test
    public void testBlankCategory() {
        String blankName = " ";
        String emptyName = "";

        database.inTransaction(() -> parentCategoriesDao.addParentCategory(blankName));
        database.inTransaction(() -> parentCategoriesDao.addParentCategory(emptyName));

        List<ParentCategoryEntity> allCategories = database.inTransaction(() -> parentCategoriesDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(CategoryEntity.NONE_CATEGORY, allCategories.get(0).getName());
    }

    @Test
    public void testSetParentCategory() {
        String categoryName = "Groceries";
        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        CategoryEntity savedEntity = database.inTransaction(() -> categoriesDao.addCategory(categoryName));

        String parentCategoryName = "Food";
        ParentCategoryEntity savedParentCategory  = database.inTransaction(() -> parentCategoriesDao.addParentCategory(parentCategoryName));

        savedEntity.setParentCategory(savedParentCategory);
        CategoryEntity categoryWithParent = database.inTransaction(() -> categoriesDao.save(savedEntity));

        Assertions.assertEquals(savedParentCategory.getId(), categoryWithParent.getParentCategory().getId());
    }
}
