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

import static org.junit.Assert.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CategoryDAOTest {
    public DAOTestExtension database = DAOTestExtension.newBuilder()
            .addEntityClass(CategoryEntity.class)
            .addEntityClass(ParentCategoryEntity.class)
            .build();
    private CategoryDAO categoryDao;

    @BeforeEach
    public void setUp() {
        categoryDao = new CategoryDAO(database.getSessionFactory());
    }

    @Test
    public void testSaveCategory() {
        String categoryName = "Groceries";
        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        CategoryEntity savedEntity = database.inTransaction(() -> categoryDao.save(categoryEntity));

        List<CategoryEntity> allCategories = database.inTransaction(() -> categoryDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(categoryName, allCategories.get(0).getName());

        CategoryEntity actualEntity = database.inTransaction(() -> categoryDao.findByName(categoryName)).orElse(null);
        Assertions.assertNotNull(actualEntity);
        Assertions.assertEquals(categoryName, actualEntity.getName());
    }

    /**
     * Confirms that we cannot write two categories of the same name
     */
    @Test
    public void testSaveIfDoesNotExist() {
        String categoryName = "Groceries";
        CategoryEntity categoryEntity1 = new CategoryEntity(categoryName);
        CategoryEntity savedEntity1 = database.inTransaction(() -> categoryDao.save(categoryEntity1));

        // Attempt to save a category with the same name
        CategoryEntity categoryEntity2 = new CategoryEntity(categoryName);
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () ->{
            database.inTransaction(() -> categoryDao.save(categoryEntity2));
        });
        Assertions.assertEquals("could not execute statement", e.getMessage());
    }

    @Test
    public void testSaveIfAbsent() {
        String categoryName = "Groceries";
        CategoryEntity savedEntity1 = database.inTransaction(() -> categoryDao.addCategory(categoryName));

        // Attempt to save a category with the same name
        CategoryEntity savedEntity2 = database.inTransaction(() -> categoryDao.addCategory(categoryName));
        Assertions.assertEquals(savedEntity1, savedEntity2);
    }

    @Test
    public void testBlankCategory() {
        String blankName = " ";
        String emptyName = "";

        database.inTransaction(() -> categoryDao.addCategory(blankName));
        database.inTransaction(() -> categoryDao.addCategory(emptyName));

        List<CategoryEntity> allCategories = database.inTransaction(() -> categoryDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(CategoryEntity.NONE_CATEGORY, allCategories.get(0).getName());
    }
}
