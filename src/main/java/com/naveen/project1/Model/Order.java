package com.naveen.project1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Email
    @Column(nullable = false)
    private String email;

    private LocalDate orderDate;
    private double totalAmount;
    private String orderStatus;

    //order Relationship to orderitems

    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<OrderItem> orderItems = new ArrayList<>();

    //order relationship to payment

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    //order relationship to address

    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

//    public Payment getPayment() {
//        return payment;
//    }
//
//    public void setPayment(Payment payment) {
//        this.payment = payment;
//    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }
}
