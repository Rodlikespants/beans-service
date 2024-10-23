package db.daos;

import db.entities.transactions.BeansTransactionEntity;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class BeansTransactionDAOTest {
    public DAOTestExtension database = DAOTestExtension.newBuilder().addEntityClass(BeansTransactionEntity.class).build();
    private BeansTransactionDAO beansTxnDao;

    @BeforeEach
    public void setUp() {
        beansTxnDao = new BeansTransactionDAO(database.getSessionFactory());
    }

    @Test
    public void testSaveBeansTransaction() {
        String categoryName = "Groceries";
        BeansTransactionEntity txn = new BeansTransactionEntity(
                null,
                1L,
                BeansTransactionEntity.Direction.DEBIT,
                BigDecimal.TEN,
                new Date(Instant.now().toEpochMilli()),
                "This is a test transaction",
                categoryName,
                BeansTransactionEntity.Source.AMEX,
                true
        );
        BeansTransactionEntity savedTxn = database.inTransaction(() -> beansTxnDao.save(txn));

        List<BeansTransactionEntity> txns = database.inTransaction(() -> beansTxnDao.findAll());
        Assertions.assertEquals(1, txns.size());
        Assertions.assertEquals(categoryName, txns.get(0).getCategory());

        long txnId = savedTxn.getId();
        BeansTransactionEntity actualEntity = database.inTransaction(() -> beansTxnDao.findById(txnId).orElse(null));
        Assertions.assertNotNull(actualEntity);
        Assertions.assertEquals(categoryName, actualEntity.getCategory());
    }

    @Test
    public void testSavingUniqueHashText() {
        String categoryName = "Groceries";
        BeansTransactionEntity txn1 = new BeansTransactionEntity(
                null,
                1L,
                BeansTransactionEntity.Direction.DEBIT,
                BigDecimal.TEN,
                new Date(Instant.now().toEpochMilli()),
                "This is a test transaction",
                categoryName,
                BeansTransactionEntity.Source.AMEX,
                true
        );
        BeansTransactionEntity savedTxn1 = database.inTransaction(() -> beansTxnDao.saveUnique(txn1));

        BeansTransactionEntity txn2 = new BeansTransactionEntity(
                null,
                1L,
                BeansTransactionEntity.Direction.DEBIT,
                BigDecimal.TEN,
                new Date(Instant.now().toEpochMilli()),
                "This is a test transaction",
                categoryName,
                BeansTransactionEntity.Source.AMEX,
                true
        );
        BeansTransactionEntity savedTxn2 = database.inTransaction(() -> beansTxnDao.saveUnique(txn2));

        List<BeansTransactionEntity> txns = database.inTransaction(() -> beansTxnDao.findAll());
        Assertions.assertEquals(1, txns.size());

        Assertions.assertEquals(savedTxn1.getHashtext(), savedTxn2.getHashtext());
    }
}
