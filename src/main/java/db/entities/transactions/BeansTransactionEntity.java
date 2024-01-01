package db.entities.transactions;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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

    @Column(name = "direction")
    private Direction direction;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "effectiveDate")
    private Date effectiveDate;

    @Column(name = "description")
    private String description;

    // TODO change this later to an enum

    @Column(name = "category")
    private String category;

    public BeansTransactionEntity() {}

    public BeansTransactionEntity(Long id, Direction direction, BigDecimal amount, Date effectiveDate, String description, String category) {
        this.id = id;
        this.direction = direction;
        this.amount = amount;
        this.effectiveDate = effectiveDate;
        this.description = description;
        this.category = category;
    }

    public Long getId() {
        return id;
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
        BeansTransactionEntity that = (BeansTransactionEntity) o;
        return Objects.equals(id, that.id)
                && direction == that.direction
                && Objects.equals(amount, that.amount)
                && Objects.equals(effectiveDate, that.effectiveDate)
                && Objects.equals(description, that.description)
                && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, direction, amount, effectiveDate, description, category);
    }

    @Override
    public String toString() {
        return "BeansTransactionEntity{" +
                "id=" + id +
                ", direction=" + direction +
                ", amount=" + amount +
                ", effectiveDate=" + effectiveDate +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
