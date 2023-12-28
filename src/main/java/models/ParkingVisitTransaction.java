package models;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Objects;

public class ParkingVisitTransaction {
    private long txnId;
    private long siteId;
    private long userId;
    private long vehicleId;
    private PaymentStatus paymentStatus;

    private DateTime entryTime;

    private DateTime exitTime;

    private BigDecimal price;

    public enum PaymentStatus {
        paymentForegone("Payment Foregone"),
        paymentNotRequired("Payment Not Required"),
        paymentCompleted("Payment Completed");

        private String name;
        PaymentStatus(String name) {
            this.name = name;
        }

        public static PaymentStatus convert(String value) {
            switch (value) {
                case "Payment Foregone" -> {
                    return paymentForegone;
                }
                case "Payment Not Required" -> {
                    return paymentNotRequired;
                }
                case "Payment Completed" -> {
                    return paymentCompleted;
                }
                default -> {
                }
            }
            return null;
        }
    }


    public ParkingVisitTransaction() {

    }

    public ParkingVisitTransaction(long txnId, long siteId, long userId, long vehicleId, PaymentStatus paymentStatus, DateTime entryTime, DateTime exitTime, BigDecimal price) {
        this.txnId = txnId;
        this.siteId = siteId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.paymentStatus = paymentStatus;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.price = price;
    }

    public long getTxnId() {
        return txnId;
    }

    public long getSiteId() {
        return siteId;
    }

    public long getUserId() {
        return userId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public DateTime getEntryTime() {
        return entryTime;
    }

    public DateTime getExitTime() {
        return exitTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingVisitTransaction that = (ParkingVisitTransaction) o;
        return txnId == that.txnId && siteId == that.siteId && userId == that.userId && vehicleId == that.vehicleId && paymentStatus == that.paymentStatus && Objects.equals(entryTime, that.entryTime) && Objects.equals(exitTime, that.exitTime) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(txnId, siteId, userId, vehicleId, paymentStatus, entryTime, exitTime, price);
    }

    @Override
    public String toString() {
        return "ParkingVisitTransaction{" +
                "txnId=" + txnId +
                ", siteId=" + siteId +
                ", userId=" + userId +
                ", vehicleId=" + vehicleId +
                ", paymentStatus=" + paymentStatus +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", price=" + price +
                '}';
    }
}
