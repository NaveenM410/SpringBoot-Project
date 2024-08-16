package com.naveen.project1.Controller;

import com.naveen.project1.Model.Address;
import com.naveen.project1.Model.User;
import com.naveen.project1.Service.AddressService;
import com.naveen.project1.payload.AddressDTO;
import com.naveen.project1.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController
{
    @Autowired
    private AddressService addressService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO)
    {
        User user = authUtil.loggedInUser();
        AddressDTO savedaddressDTO = addressService.createAddress(addressDTO,user);
        return new ResponseEntity<>(savedaddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses()
    {
        List<AddressDTO> addressDTO = addressService.getAllAddress();
        return new ResponseEntity<>(addressDTO,HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> GetSpecificAddress(@PathVariable Long addressId)
    {
        AddressDTO addressDTO = addressService.getSpecificAddress(addressId);
        return new ResponseEntity<>(addressDTO,HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUsersAddresses()
    {
        User user = authUtil.loggedInUser();
        List<AddressDTO> usersaddresses = addressService.getUsersAddresses(user);
        return new ResponseEntity<>(usersaddresses,HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                        @RequestBody AddressDTO addressDTO)
    {
        AddressDTO updatedAddress = addressService.updateAddress(addressId,addressDTO);
        return new ResponseEntity<>(updatedAddress,HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId)
    {
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}

 
























