package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Order {
    private long id;
    private Source source;

    public Order() {}

    public Order(long id, Source source) {
        this.id = id;
        this.source = source;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && source == order.source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", source=" + source +
                '}';
    }
}
