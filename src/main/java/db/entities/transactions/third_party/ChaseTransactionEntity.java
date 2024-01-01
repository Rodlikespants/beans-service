package db.entities.transactions.third_party;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "chase_transactions")
public class ChaseTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "details")
    private String details;

    @Column(name = "postingDate")
    private Date postingDate;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    // TODO change this to an enum later

    @Column(name = "type")
    private String type;


    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "checkOrSlipNumber")
    private Integer checkOrSlipNumber;

    public ChaseTransactionEntity() {}

    public ChaseTransactionEntity(Long id, String details, Date postingDate, String description, BigDecimal amount, String type, BigDecimal balance, Integer checkOrSlipNumber) {
        this.id = id;
        this.details = details;
        this.postingDate = postingDate;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.balance = balance;
        this.checkOrSlipNumber = checkOrSlipNumber;
    }

    public long getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getCheckOrSlipNumber() {
        return checkOrSlipNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChaseTransactionEntity that = (ChaseTransactionEntity) o;
        return id == that.id
                && Objects.equals(details, that.details)
                && Objects.equals(postingDate, that.postingDate)
                && Objects.equals(description, that.description)
                && Objects.equals(amount, that.amount)
                && Objects.equals(type, that.type)
                && Objects.equals(balance, that.balance)
                && Objects.equals(checkOrSlipNumber, that.checkOrSlipNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details, postingDate, description, amount, type, balance, checkOrSlipNumber);
    }

    @Override
    public String toString() {
        return "ChaseTransactionEntity{" +
                "id=" + id +
                ", details='" + details + '\'' +
                ", postingDate=" + postingDate +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", checkOrSlipNumber=" + checkOrSlipNumber +
                '}';
    }
}
