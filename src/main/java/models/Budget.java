package models;

import models.categories.ParentCategory;
import models.transactions.BeansTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Budget {
    private Long id;
    private long userId;
    private ParentCategory parentCategory;
    private BigDecimal total;

    List<BeansTransaction> transactions;

    public Budget(Long id, long userId, ParentCategory parentCategory, BigDecimal total, List<BeansTransaction> transactions) {
        this.id = id;
        this.userId = userId;
        this.parentCategory = parentCategory;
        this.total = total;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public ParentCategory getParentCategory() {
        return parentCategory;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<BeansTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return userId == budget.userId && Objects.equals(id, budget.id) && Objects.equals(parentCategory, budget.parentCategory) && Objects.equals(total, budget.total) && Objects.equals(transactions, budget.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, parentCategory, total, transactions);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", userId=" + userId +
                ", parentCategory=" + parentCategory +
                ", amount=" + total +
                ", transactions=" + transactions +
                '}';
    }
}
