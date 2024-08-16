package com.naveen.project1.Repository;

import com.naveen.project1.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>
{

}
