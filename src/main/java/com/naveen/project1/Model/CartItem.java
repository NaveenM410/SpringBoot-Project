package com.naveen.project1.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
public class CartItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartItemId;

    //CartItem Relationship to Cart

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    //CartItem Relationship to Product

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private double discount;
    private double productPrice;

    public Long getCartItemId() {
        return CartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        CartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
