package db.daos;

import db.entities.categories.CategoryEntity;
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
public class CategoriesDAOTest {
    public DAOTestExtension database = DAOTestExtension.newBuilder().addEntityClass(CategoryEntity.class).build();
    private CategoriesDAO categoriesDao;

    @BeforeEach
    public void setUp() {
        categoriesDao = new CategoriesDAO(database.getSessionFactory());
    }

    @Test
    public void testSaveCategory() {
        String categoryName = "Groceries";
        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        CategoryEntity savedEntity = database.inTransaction(() -> categoriesDao.save(categoryEntity));

        List<CategoryEntity> allCategories = database.inTransaction(() -> categoriesDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(categoryName, allCategories.get(0).getName());

        CategoryEntity actualEntity = database.inTransaction(() -> categoriesDao.findByName(categoryName)).orElse(null);
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
        CategoryEntity savedEntity1 = database.inTransaction(() -> categoriesDao.save(categoryEntity1));

        // Attempt to save a category with the same name
        CategoryEntity categoryEntity2 = new CategoryEntity(categoryName);
        Exception e = Assertions.assertThrows(ConstraintViolationException.class, () ->{
            database.inTransaction(() -> categoriesDao.save(categoryEntity2));
        });
        Assertions.assertEquals("could not execute statement", e.getMessage());
    }

    @Test
    public void testSaveIfAbsent() {
        String categoryName = "Groceries";
        CategoryEntity savedEntity1 = database.inTransaction(() -> categoriesDao.addCategory(categoryName));

        // Attempt to save a category with the same name
        CategoryEntity savedEntity2 = database.inTransaction(() -> categoriesDao.addCategory(categoryName));
        Assertions.assertEquals(savedEntity1, savedEntity2);
    }

    @Test
    public void testBlankCategory() {
        String blankName = " ";
        String emptyName = "";

        database.inTransaction(() -> categoriesDao.addCategory(blankName));
        database.inTransaction(() -> categoriesDao.addCategory(emptyName));

        List<CategoryEntity> allCategories = database.inTransaction(() -> categoriesDao.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(CategoryEntity.NONE_CATEGORY, allCategories.get(0).getName());
    }
}
