package db.entities.transactions;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/*
CREATE TABLE beans_txns (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    direction VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2),
    effective_date date,
    description VARCHAR(255) DEFAULT NULL,
    category VARCHAR(255),
    active TINYINT(1) NOT NULL DEFAULT 1
);
 */

//@Entity(name="BeansTransactionEntity")
@Entity
@Table(name = "beans_txns")
public class BeansTransactionEntity {

    public enum Direction {
        CREDIT,
        DEBIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "userId")
    @Column(name = "user_id")
    private long userId;

    @Column(name = "direction")
    private Direction direction;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "description")
    private String description;

    // TODO change this later to an enum

    @Column(name = "category")
    private String category;

    @Column(name = "active")
    private boolean isActive;

    public BeansTransactionEntity() {}

    public BeansTransactionEntity(Long id, long userId, Direction direction, BigDecimal amount, Date effectiveDate, String description, String category, boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.direction = direction;
        this.amount = amount;
        this.effectiveDate = effectiveDate;
        this.description = description;
        this.category = category;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeansTransactionEntity that = (BeansTransactionEntity) o;
        return userId == that.userId && isActive == that.isActive && Objects.equals(id, that.id) && direction == that.direction && Objects.equals(amount, that.amount) && Objects.equals(effectiveDate, that.effectiveDate) && Objects.equals(description, that.description) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, direction, amount, effectiveDate, description, category, isActive);
    }

    @Override
    public String toString() {
        return "BeansTransactionEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", direction=" + direction +
                ", amount=" + amount +
                ", effectiveDate=" + effectiveDate +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
