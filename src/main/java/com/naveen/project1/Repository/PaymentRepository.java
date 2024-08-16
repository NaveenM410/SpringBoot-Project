package com.naveen.project1.Repository;

import com.naveen.project1.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>
{

}
