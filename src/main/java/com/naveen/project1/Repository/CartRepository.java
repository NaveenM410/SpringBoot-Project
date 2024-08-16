package com.naveen.project1.Repository;

import com.naveen.project1.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
    Cart findCartByEmail(String email);

    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 AND c.id=?2")
    Cart findCartByEmailandCartId(String emailId, Long cartId);
}
