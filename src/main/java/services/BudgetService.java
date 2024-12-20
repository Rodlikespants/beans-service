package services;

import db.daos.BeansTransactionDAO;
import db.daos.categories.CategoryDAO;
import db.daos.categories.ParentCategoryDAO;
import db.entities.categories.ParentCategoryEntity;
import db.entities.transactions.BeansTransactionEntity;
import db.mappers.BeansTransactionMapper;
import db.mappers.CategoryMapper;
import models.Budget;
import models.categories.Category;
import models.categories.ParentCategory;
import models.transactions.BeansTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class BudgetService {
    private BeansTransactionDAO beansTransactionDao;
    private CategoryDAO categoryDao;
    private ParentCategoryDAO parentCategoryDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetService.class);

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

    public List<Budget> getAllBudgets(long userId, LocalDate start, LocalDate end, BeansTransaction.Source source) {
        List<BeansTransaction> beansTransactions = beansTransactionDao
                .findUserTransactions(userId, start, end, BeansTransactionEntity.Source.valueOf(source.name()))
                .stream().map(BeansTransactionMapper::toBeansTransaction).toList();
        Map<String, List<BeansTransaction>> txnsByCategory = beansTransactions.stream()
                .collect(groupingBy(BeansTransaction::getCategory));

        Map<ParentCategory, Budget> budgetsByParent = new HashMap<>();
        for (String categoryStr: txnsByCategory.keySet()) {
            Category category = categoryDao.findByName(categoryStr).map(CategoryMapper::toCategory).orElse(null);
            if (category != null) {

                // check parent if transactions and amount exists already
                Optional<Budget> budgetOpt               = Optional.of(category.getParentCategory()).map(budgetsByParent::get);
                List<BeansTransaction> totalTransactions = budgetOpt.map(Budget::getTransactions).orElse(new ArrayList<>());
                BigDecimal total                         = budgetOpt.map(Budget::getTotal).orElse(BigDecimal.ZERO);
                List<BeansTransaction> txns              = txnsByCategory.get(categoryStr);
                for (BeansTransaction txn : txns) {
                    if (txn.getDirection() == BeansTransaction.Direction.CREDIT) {
                        total = total.add(txn.getAmount().multiply(BigDecimal.valueOf(-1)));
                    } else {
                        total = total.add(txn.getAmount());
                    }
                }

                totalTransactions.addAll(txns);
                if (category.getParentCategory() != null) {
                    Budget budget = new Budget(
                            null,
                            userId,
                            category.getParentCategory(),
                            total,
                            totalTransactions);
                    budgetsByParent.put(category.getParentCategory(), budget);
                }
            }

//            LOGGER.info("Budget Calculation: ${} dollars spent in Category={} with {} transactions", total, category, txns.size());
        }


        return budgetsByParent.values().stream().toList();
    }
}
