package models;

import models.categories.ParentCategory;
import models.transactions.BeansTransaction;

import java.math.BigDecimal;
import java.util.List;

public class Budget {
    private long id;
    private long userId;
    private ParentCategory parentCategory;
    private BigDecimal amount;

    List<BeansTransaction> transactions;
}
