package com.naveen.project1.Controller;

import com.naveen.project1.Model.Cart;
import com.naveen.project1.Repository.CartRepository;
import com.naveen.project1.Service.CartService;
import com.naveen.project1.payload.CartDTO;
import com.naveen.project1.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController
{
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity)
    {
        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    //Fetching All Carts

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOs = cartService.getAllCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.FOUND);
    }

    //Get User Cart

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById()
    {
        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDTO =  cartService.getCart(emailId,cartId);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);

    }
        ///The below method is update the ProductQuantity in Cart

    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
                                                     @PathVariable String operation)
    {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
    }

    //The below method is delete the product from Cart

    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId,
                                                        @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);

        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}

























