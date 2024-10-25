package db.entities.transactions;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
    source VARCHAR(255) NOT NULL,
    hashtext VARCHAR(255) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1
);
ALTER TABLE beans_txns ADD INDEX `beans_txn_hashtext_index` (`hashtext`);
 */

//@Entity(name="BeansTransactionEntity")
@Entity
@Table(name = "beans_txns")
public class BeansTransactionEntity {

    public enum Direction {
        CREDIT,
        DEBIT
    }

    public enum Source {
        AMEX,
        CHASE
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
    private LocalDate effectiveDate;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "source")
    private Source source;

    @Column(name = "hashtext")
    private String hashtext;
    @Column(name = "active")
    private boolean isActive;

    public BeansTransactionEntity() {}

    public BeansTransactionEntity(Long id, long userId, Direction direction, BigDecimal amount, LocalDate effectiveDate, String description, String category, Source source, boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.direction = direction;
        this.amount = amount;
        this.effectiveDate = effectiveDate;
        this.description = description;
        this.category = category;
        this.source = source;
        this.isActive = isActive;
        this.hashtext = generateHashText();
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        this.hashtext = generateHashText();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.hashtext = generateHashText();
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        this.hashtext = generateHashText();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.hashtext = generateHashText();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.hashtext = generateHashText();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Source getSource() {
        return source;
    }

    public String getHashtext() {
        return hashtext;
    }

    public String generateHashText() {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            String inputText = String.valueOf(userId) +
                    direction +
                    amount +
                    effectiveDate +
                    description +
                    category +
                    source;
            byte[] messageDigest = md.digest(inputText.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeansTransactionEntity that = (BeansTransactionEntity) o;
        return userId == that.userId && isActive == that.isActive && Objects.equals(id, that.id) && direction == that.direction && Objects.equals(amount, that.amount) && Objects.equals(effectiveDate, that.effectiveDate) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && source == that.source && Objects.equals(hashtext, that.hashtext);
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
                ", source=" + source +
                ", hashtext='" + hashtext + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, direction, amount, effectiveDate, description, category, source, isActive);
    }
}
