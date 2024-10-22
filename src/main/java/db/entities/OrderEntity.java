package db.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class OrderEntity {
    /**
     * Entity's unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * order source.
     */
    @Column(name = "source")
    private String source;

    // example join column annotation
    // @OneToOne(cascade = CascadeType.ALL)
    //    @JoinColumn(name = "address_id", referencedColumnName = "id")

    // example annotation for joining another entity by a specific field
    // @OneToOne(mappedBy = "address")

    public OrderEntity() {}

    public OrderEntity(long id, String source) {
        this.id = id;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", source='" + source + '\'' +
                '}';
    }
}
