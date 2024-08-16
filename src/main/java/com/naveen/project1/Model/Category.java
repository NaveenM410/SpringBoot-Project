package com.naveen.project1.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name="categories")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min=5,message = "Category name contain aleast 5 characters")
    private String categoryName;

    //Category relationship to Products

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public @NotBlank @Size(min = 5, message = "Category name contain aleast 5 characters") String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotBlank @Size(min = 5, message = "Category name contain aleast 5 characters") String categoryName) {
        this.categoryName = categoryName;
    }
}
