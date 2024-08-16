package com.naveen.project1.Service;

import com.naveen.project1.Exceptions.APIException;
import com.naveen.project1.Exceptions.ResourceNotFoundException;
import com.naveen.project1.Model.Cart;
import com.naveen.project1.Model.CartItem;
import com.naveen.project1.Model.Product;
import com.naveen.project1.Repository.CartItemRepository;
import com.naveen.project1.Repository.CartRepository;
import com.naveen.project1.Repository.ProductRepository;
import com.naveen.project1.payload.CartDTO;
import com.naveen.project1.payload.ProductDTO;
import com.naveen.project1.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthUtil authUtil;   /*---->it is a class helps auth related tasks; */

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity)
    {
        Cart cart  = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);

        if (cartItem != null) {
            throw new APIException("Product " + product.getProductName() + " already exists in the cart");
        }

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        cartDTO.setProducts(productStream.toList());

        return cartDTO;

    }

    private Cart createCart()
    {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart!=null)
            return userCart;
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if (carts.size() == 0) {
            throw new APIException("No cart exists");
        }

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).collect(Collectors.toList());

        return cartDTOs;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailandCartId(emailId,cartId);
        if(cart==null)
            throw new ResourceNotFoundException("Cart","cartId",cartId);
        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        cart.getCartItems().forEach(c ->
                c.getProduct().setQuantity(c.getQuantity()));
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p ->modelMapper.map(p.getProduct(),ProductDTO.class))
                .toList();
        cartDTO.setProducts(products);
        return cartDTO;
    }


    @Transactional
    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {

        String emailId = authUtil.loggedInEmail();
        Cart userCart = cartRepository.findCartByEmail(emailId);
        Long cartId  = userCart.getCartId();

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        //the below two lines of code tells if the quantity of product is lessthan 0 will throw an below exception

        int newQuantity = cartItem.getQuantity() + quantity;
        if(newQuantity < 0)
            throw new APIException("The resulting quantity cannot be negative");

        if(newQuantity == 0)
            deleteProductFromCart(cartId,productId);
        else
        {
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
            cartRepository.save(cart);
        }
        CartItem updatedItem = cartItemRepository.save(cartItem);
        if(updatedItem.getQuantity() == 0){
            cartItemRepository.deleteById(updatedItem.getCartItemId());
        }


        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO prd = modelMapper.map(item.getProduct(), ProductDTO.class);
            prd.setQuantity(item.getQuantity());
            return prd;
        });


        cartDTO.setProducts(productStream.toList());

        return cartDTO;
    }

    @Transactional
    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }
}

























