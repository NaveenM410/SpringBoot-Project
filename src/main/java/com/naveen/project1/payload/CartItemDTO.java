package com.naveen.project1.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO
{
    private Long cartItemId;
    private Integer quantity;
    private CartDTO cart;
    private ProductDTO productDTO;
    private double discount;
    private double productPrice;

}
