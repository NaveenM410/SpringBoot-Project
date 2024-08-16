package com.naveen.project1.Repository;

import com.naveen.project1.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long>
{

}
