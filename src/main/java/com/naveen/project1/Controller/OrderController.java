package com.naveen.project1.Controller;

import com.naveen.project1.Service.OrderService;
import com.naveen.project1.payload.OrderDTO;
import com.naveen.project1.payload.OrderRequestDTO;
import com.naveen.project1.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController
{
    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDTO orderRequestDTO)
    {
        String emailId = authUtil.loggedInEmail();
        OrderDTO orderproducts = orderService.placeOrder(
                emailId,
                orderRequestDTO.getAddressId(),
                paymentMethod,
                orderRequestDTO.getPgName(),
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage()
        );
        return new ResponseEntity<>(orderproducts, HttpStatus.CREATED);
    }

}
