package com.naveen.project1.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private double totalPrice = 0.0;

    //Cart Relationship to CartItems

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();


    //Cart Relationship to User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
