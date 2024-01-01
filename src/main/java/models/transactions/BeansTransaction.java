package models.transactions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class BeansTransaction {
    public enum Direction {
        CREDIT,
        DEBIT
    }

    private Direction direction;
    private BigDecimal amount;
    private Date effectiveDate;
    private String description;

    // TODO change this later to an enum
    private String category;

    public BeansTransaction(Direction direction, BigDecimal amount, Date effectiveDate, String description, String category) {
        this.direction = direction;
        this.amount = amount;
        this.effectiveDate = effectiveDate;
        this.description = description;
        this.category = category;
    }

    public Direction getDirection() {
        return direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeansTransaction that = (BeansTransaction) o;
        return direction == that.direction
                && Objects.equals(amount, that.amount)
                && Objects.equals(effectiveDate, that.effectiveDate)
                && Objects.equals(description, that.description)
                && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, amount, effectiveDate, description, category);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "direction=" + direction +
                ", amount=" + amount +
                ", effectiveDate=" + effectiveDate +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
