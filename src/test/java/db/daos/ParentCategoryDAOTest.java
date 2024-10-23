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
public class ParentCategoryDAOTest {
    public DAOTestExtension database = DAOTestExtension.newBuilder()
            .addEntityClass(ParentCategoryEntity.class)
            .addEntityClass(CategoryEntity.class)
            .build();
    private ParentCategoryDAO parentCategoryDao;
    private CategoryDAO categoryDao;

    @BeforeEach
    public void setUp() {
        parentCategoryDao = new ParentCategoryDAO(database.getSessionFactory());
        categoryDao = new CategoryDAO(database.getSessionFactory());
    }

    @Test
    public void testSaveParentCategory() {
        String categoryName = "Groceries";
        ParentCategoryEntity savedEntity = database.inTransaction(() -> parentCategoryDao.addParentCategory(categoryName));

        List<ParentCategoryEntity> allCategories = database.inTransaction(() -> parentCategoryDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(categoryName, allCategories.get(0).getName());

        ParentCategoryEntity actualEntity = database.inTransaction(() -> parentCategoryDao.findByName(categoryName)).orElse(null);
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
        ParentCategoryEntity savedEntity1 = database.inTransaction(() -> parentCategoryDao.save(categoryEntity1));

        // Attempt to save a category with the same name
        ParentCategoryEntity categoryEntity2 = new ParentCategoryEntity(categoryName);
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () ->{
            database.inTransaction(() -> parentCategoryDao.save(categoryEntity2));
        });
        Assertions.assertEquals("could not execute statement", e.getMessage());
    }

    @Test
    public void testSaveIfAbsent() {
        String categoryName = "Groceries";
        ParentCategoryEntity savedEntity1 = database.inTransaction(() -> parentCategoryDao.addParentCategory(categoryName));

        // Attempt to save a category with the same name
        ParentCategoryEntity savedEntity2 = database.inTransaction(() -> parentCategoryDao.addParentCategory(categoryName));
        Assertions.assertEquals(savedEntity1, savedEntity2);
    }

    @Test
    public void testBlankCategory() {
        String blankName = " ";
        String emptyName = "";

        database.inTransaction(() -> parentCategoryDao.addParentCategory(blankName));
        database.inTransaction(() -> parentCategoryDao.addParentCategory(emptyName));

        List<ParentCategoryEntity> allCategories = database.inTransaction(() -> parentCategoryDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(CategoryEntity.NONE_CATEGORY, allCategories.get(0).getName());
    }

    @Test
    public void testSetParentCategory() {
        String categoryName = "Groceries";
        CategoryEntity savedEntity = database.inTransaction(() -> categoryDao.addCategory(categoryName));

        String parentCategoryName = "Food";
        ParentCategoryEntity savedParentCategory  = database.inTransaction(() -> parentCategoryDao.addParentCategory(parentCategoryName));

        savedEntity.setParentCategory(savedParentCategory);
        CategoryEntity categoryWithParent = database.inTransaction(() -> categoryDao.save(savedEntity));

        Assertions.assertEquals(savedParentCategory.getId(), categoryWithParent.getParentCategory().getId());
    }
}
