package com.naveen.project1.Service;

import com.naveen.project1.Model.User;
import com.naveen.project1.payload.AddressDTO;

import java.util.List;

public interface AddressService
{

    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAllAddress();

    AddressDTO getSpecificAddress(Long addressId);


    List<AddressDTO> getUsersAddresses(User user);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);


    String deleteAddress(Long addressId);
}
