package com.naveen.project1.Service;

import com.naveen.project1.Exceptions.APIException;
import com.naveen.project1.Exceptions.ResourceNotFoundException;
import com.naveen.project1.Model.*;
import com.naveen.project1.Repository.*;
import com.naveen.project1.payload.OrderDTO;
import com.naveen.project1.payload.OrderItemDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductRepository productRepository;


    @Override
    @Transactional
    public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage)
    {
        //checking the cart
        Cart cart = cartRepository.findCartByEmail(emailId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "email", emailId);
        }

        //getting the address to placeOrder
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        //placedOrder operation
        Order order = new Order();
        order.setEmail(emailId);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted !");
        order.setAddress(address);

        //savedorder transfer to payment

        Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
        payment.setOrder(order);//order transfer to payment
        payment = paymentRepository.save(payment);//In paymentrepo can be saved
        order.setPayment(payment);//we r setting the payment

        Order savedOrder = orderRepository.save(order);//order can be saved in orderrepo

        //getting the cartitems

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new APIException("Cart is empty");
        }

        //cartitems can be transferred to orderitems

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);//orderitem can be saved in orderitem repo

        //The Below method is to update the stock in database because order can be purchased by the user and quantity can be reduced
        //thats why we need to update the stock

        cart.getCartItems().forEach(item -> {
            int quantity = item.getQuantity();
            Product product = item.getProduct();

            // Reduce stock quantity
            product.setQuantity(product.getQuantity() - quantity);

            // Save product back to the database
            productRepository.save(product);

            // Remove items from cart
            cartService.deleteProductFromCart(cart.getCartId(), item.getProduct().getProductId());
        });

        //mapping the savedorder to orderDTo
        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        //In OrderDTO is mapping to OrderItemDTO
        orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));

        orderDTO.setAddressId(addressId);

        return orderDTO;
    }

}
