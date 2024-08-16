package com.naveen.project1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min=5,message = "Street name must be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min=5,message = "Building name must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min=2,message = "City name must be atleast 2 characters")
    private String city;

    @NotBlank
    @Size(min=2,message = "State name must be atleast 2 characters")
    private String state;

    @NotBlank
    @Size(min=2,message = "Country name must be atleast 2 characters")
    private String country;

    @NotBlank
    @Size(min=5,message = "Pincode must be atleast 5 characters")
    private String pincode;

    //Address Relationship to User

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @NotBlank @Size(min = 2, message = "Country name must be atleast 2 characters") String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank @Size(min = 2, message = "Country name must be atleast 2 characters") String country) {
        this.country = country;
    }

    public @NotBlank @Size(min = 2, message = "State name must be atleast 2 characters") String getState() {
        return state;
    }

    public void setState(@NotBlank @Size(min = 2, message = "State name must be atleast 2 characters") String state) {
        this.state = state;
    }

    public @NotBlank @Size(min = 2, message = "City name must be atleast 2 characters") String getCity() {
        return city;
    }

    public void setCity(@NotBlank @Size(min = 2, message = "City name must be atleast 2 characters") String city) {
        this.city = city;
    }

    public @NotBlank @Size(min = 5, message = "Street name must be atleast 5 characters") String getStreet() {
        return street;
    }

    public void setStreet(@NotBlank @Size(min = 5, message = "Street name must be atleast 5 characters") String street) {
        this.street = street;
    }

    public @NotBlank @Size(min = 5, message = "Pincode must be atleast 5 characters") String getPincode() {
        return pincode;
    }

    public void setPincode(@NotBlank @Size(min = 5, message = "Pincode must be atleast 5 characters") String pincode) {
        this.pincode = pincode;
    }

    public @NotBlank @Size(min = 5, message = "Building name must be atleast 5 characters") String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(@NotBlank @Size(min = 5, message = "Building name must be atleast 5 characters") String buildingName) {
        this.buildingName = buildingName;
    }
}
