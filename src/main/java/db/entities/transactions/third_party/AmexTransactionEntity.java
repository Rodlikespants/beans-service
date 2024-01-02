package db.entities.transactions.third_party;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "amex_transactions")
public class AmexTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "extended_details")
    private String extendedDetails;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    // TODO change this to an enum later
    @Column(name = "category")
    private String category;

    @Column(name = "appears_as")
    private String appearsOnYourStatementAs;

    @Column(name = "address")
    private String address;

    @Column(name = "city_state")
    private String cityAndState;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "reference")
    private String reference;

    public AmexTransactionEntity() {}

    public AmexTransactionEntity(Long id, String extendedDetails, Date date, String description, BigDecimal amount, String category, String appearsOnYourStatementAs, String address, String cityAndState, String zipCode, String country, String reference) {
        this.id = id;
        this.extendedDetails = extendedDetails;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.appearsOnYourStatementAs = appearsOnYourStatementAs;
        this.address = address;
        this.cityAndState = cityAndState;
        this.zipCode = zipCode;
        this.country = country;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public String getExtendedDetails() {
        return extendedDetails;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getAppearsOnYourStatementAs() {
        return appearsOnYourStatementAs;
    }

    public String getAddress() {
        return address;
    }

    public String getCityAndState() {
        return cityAndState;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmexTransactionEntity that = (AmexTransactionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(extendedDetails, that.extendedDetails) && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(amount, that.amount) && Objects.equals(category, that.category) && Objects.equals(appearsOnYourStatementAs, that.appearsOnYourStatementAs) && Objects.equals(address, that.address) && Objects.equals(cityAndState, that.cityAndState) && Objects.equals(zipCode, that.zipCode) && Objects.equals(country, that.country) && Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, extendedDetails, date, description, amount, category, appearsOnYourStatementAs, address, cityAndState, zipCode, country, reference);
    }

    @Override
    public String toString() {
        return "AmexTransactionEntity{" +
                "id=" + id +
                ", extendedDetails='" + extendedDetails + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", appearsOnYourStatementAs='" + appearsOnYourStatementAs + '\'' +
                ", address='" + address + '\'' +
                ", cityAndState='" + cityAndState + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
