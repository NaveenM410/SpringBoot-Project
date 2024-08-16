package com.naveen.project1.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    //OrderItem relationship to Product

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    //OrderItem relationship to order

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer Quantity;
    private double discount;
    private double orderedProductPrice;

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOrderedProductPrice() {
        return orderedProductPrice;
    }

    public void setOrderedProductPrice(double orderedProductPrice) {
        this.orderedProductPrice = orderedProductPrice;
    }
}
