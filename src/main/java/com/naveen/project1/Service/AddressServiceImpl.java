package com.naveen.project1.Service;

import com.naveen.project1.Exceptions.ResourceNotFoundException;
import com.naveen.project1.Model.Address;
import com.naveen.project1.Model.User;
import com.naveen.project1.Repository.AddressRepository;
import com.naveen.project1.Repository.UserRepository;
import com.naveen.project1.payload.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user)
    {
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddress()
    {
        List<Address> alladdressList = addressRepository.findAll();
        return alladdressList.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getSpecificAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUsersAddresses(User user) {
        List<Address> addresses = user.getAddresses();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO)
    {
        Address addressFromDb= addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));
        addressFromDb.setCity(addressDTO.getCity());
        addressFromDb.setPincode(addressDTO.getPincode());
        addressFromDb.setState(addressDTO.getState());
        addressFromDb.setCountry(addressDTO.getCountry());
        addressFromDb.setStreet(addressDTO.getStreet());
        addressFromDb.setBuildingName(addressDTO.getBuildingName());

        Address updatedAddress = addressRepository.save(addressFromDb);

        User user = addressFromDb.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId)
    {
        Address addressFromDb = addressRepository.findById(addressId)
                .orElseThrow(()->new ResourceNotFoundException("Address","addressId",addressId));

        User user = addressFromDb.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);
        addressRepository.delete(addressFromDb);

        return "Address Deleted Successfully with addressId: " + addressId;
    }
}



















