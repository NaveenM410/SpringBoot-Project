package com.naveen.project1.Model;

import com.naveen.project1.payload.CategoryDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@ToString
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 3,message = "Product must contain atleast 3 characters")
    private String productName;

    @NotBlank
    @Size(min = 3,message = "Product must contain atleast 6 characters")
    private String description;

    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
    private String image;


    ///Product Relationship to Category

    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;


    //Product Relatiosnhip to User for the seller { User will also act as Seller }

    @ManyToOne
    @JoinColumn(name="seller_id")
    private User user;

    //Product Relationship to CartItems Entity to show Which product can added to the cart

    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private List<CartItem> products = new ArrayList<>();




    public @NotBlank @Size(min = 3, message = "Product must contain atleast 3 characters") String getProductName() {
        return productName;
    }

    public void setProductName(@NotBlank @Size(min = 3, message = "Product must contain atleast 3 characters") String productName) {
        this.productName = productName;
    }

    public @NotBlank @Size(min = 3, message = "Product must contain atleast 6 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank @Size(min = 3, message = "Product must contain atleast 6 characters") String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(double specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
