package db.daos;

import db.entities.categories.CategoryEntity;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CategoriesDAOTest {
    public DAOTestExtension database = DAOTestExtension.newBuilder().addEntityClass(CategoryEntity.class).build();
    private CategoriesDAO categoriesDAO;

    @BeforeEach
    public void setUp() {
        categoriesDAO = new CategoriesDAO(database.getSessionFactory());
    }

    @Test
    public void testSaveCategory() {
        String categoryName = "Groceries";
        CategoryEntity categoryEntity = new CategoryEntity(categoryName);
        CategoryEntity savedEntity = database.inTransaction(() -> categoriesDAO.save(categoryEntity));

        List<CategoryEntity> allCategories = database.inTransaction(() -> categoriesDAO.findAll());
        Assertions.assertEquals(1, allCategories.size());
        Assertions.assertEquals(categoryName, allCategories.get(0).getName());

        CategoryEntity actualEntity = database.inTransaction(() -> categoriesDAO.findByName(categoryName)).orElse(null);
        Assertions.assertNotNull(actualEntity);
        Assertions.assertEquals(categoryName, actualEntity.getName());
    }
}
