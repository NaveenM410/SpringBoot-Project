package com.naveen.project1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotBlank
    @Size(min=4,message = "Payment method must contain atleast 4 characters")
    private String paymentMethod;

    private String pgPaymentId;                 //pg ---->PaymentGateway
    private String pgStatus;
    private String pgResponseMessage;
    private String pgName;

    //Payment relationship to Payment

    @OneToOne(mappedBy = "payment",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Order order;


    public Payment(String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName, String paymentMethod) {
        this.paymentId = paymentId;
        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
        this.paymentMethod = paymentMethod;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
