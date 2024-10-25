package services;

import db.daos.BeansTransactionDAO;
import db.daos.categories.CategoryDAO;
import db.daos.categories.ParentCategoryDAO;
import db.entities.transactions.BeansTransactionEntity;
import models.Budget;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class BudgetService {
    private BeansTransactionDAO beansTransactionDao;
    private CategoryDAO categoryDao;
    private ParentCategoryDAO parentCategoryDao;

    @Inject
    public BudgetService(
            BeansTransactionDAO beansTransactionDao,
            CategoryDAO categoryDao,
            ParentCategoryDAO parentCategoryDAO
    ) {
        this.beansTransactionDao = beansTransactionDao;
        this.categoryDao = categoryDao;
        this.parentCategoryDao = parentCategoryDAO;
    }

    List<Budget> getAllBudgets(long userId, LocalDate start, LocalDate end, BeansTransactionEntity.Source source) {
        List<BeansTransactionEntity> beansTransactions = beansTransactionDao.findUserTransactions(userId, start, end, source);
        for (BeansTransactionEntity beansTxn: beansTransactions) {
//            beansTxnDao.saveUnique(beansTxn);
        }

        Map<String, List<BeansTransactionEntity>> txnsByCategory = beansTransactions.stream()
                .collect(groupingBy(BeansTransactionEntity::getCategory));

        return null;
    }
}
