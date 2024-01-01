package models.transactions.third_party;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class ChaseTransaction {
    private String details;
    private Date postingDate;
    private String description;
    private BigDecimal amount;

    // TODO change this to an enum later
    private String type;

    private BigDecimal balance;
    private Integer checkOrSlipNumber;

    public ChaseTransaction(String details, Date postingDate, String description, BigDecimal amount, String type, BigDecimal balance, Integer checkOrSlipNumber) {
        this.details = details;
        this.postingDate = postingDate;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.balance = balance;
        this.checkOrSlipNumber = checkOrSlipNumber;
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
        ChaseTransaction that = (ChaseTransaction) o;
        return Objects.equals(details, that.details)
                && Objects.equals(postingDate, that.postingDate)
                && Objects.equals(description, that.description)
                && Objects.equals(amount, that.amount)
                && Objects.equals(type, that.type)
                && Objects.equals(balance, that.balance)
                && Objects.equals(checkOrSlipNumber, that.checkOrSlipNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details, postingDate, description, amount, type, balance, checkOrSlipNumber);
    }

    @Override
    public String toString() {
        return "ChaseTransaction{" +
                "details='" + details + '\'' +
                ", postingDate=" + postingDate +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", checkOrSlipNumber=" + checkOrSlipNumber +
                '}';
    }
}
