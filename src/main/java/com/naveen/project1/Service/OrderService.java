package com.naveen.project1.Service;

import com.naveen.project1.payload.OrderDTO;
import jakarta.transaction.Transactional;

public interface OrderService 
{
    @Transactional
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
