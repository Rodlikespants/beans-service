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

    private long userId;

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

    public BeansTransaction(Direction direction, long userId, BigDecimal amount, Date effectiveDate, String description, String category) {
        this.direction = direction;
        this.userId = userId;
        this.amount = amount;
        this.effectiveDate = effectiveDate;
        this.description = description;
        this.category = category;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
