package models;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Objects;

public class ParkingTransaction {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public DateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(DateTime entryTime) {
        this.entryTime = entryTime;
    }

    public DateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(DateTime exitTime) {
        this.exitTime = exitTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    // ID, entry time, exit time, total price, user ID, site
    private long id;
    private long userId;
    private DateTime entryTime;
    private DateTime exitTime;
    private BigDecimal totalPrice;
    private String site;

    public ParkingTransaction() {
        // might be necessary for deserialization
    }

    public ParkingTransaction(long id, long userId, DateTime entryTime, DateTime exitTime, BigDecimal totalPrice, String site) {
        this.id = id;
        this.userId = userId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalPrice = totalPrice;
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTransaction that = (ParkingTransaction) o;
        return id == that.id && userId == that.userId && Objects.equals(entryTime, that.entryTime) && Objects.equals(exitTime, that.exitTime) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(site, that.site);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, entryTime, exitTime, totalPrice, site);
    }

    @Override
    public String toString() {
        return "ParkingTransaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", totalPrice=" + totalPrice +
                ", site='" + site + '\'' +
                '}';
    }
}
