package com.truck_lagbo_backend.Drivers.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    @Lob
    private byte[] photo;

    @ElementCollection
    private List<String> vehicaltype;
    private String weight;
    private String size;
    private String registrationnumber;
    @ElementCollection
    private List<String> servicearea;
    private String price;
    private boolean active;

    public Driver() {}

    public Driver(Long id, String name, String number, byte[] photo, List<String> vehicaltype, String weight, String size, String registrationnumber, List<String> servicearea, String price, boolean active) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photo = photo;
        this.vehicaltype = vehicaltype;
        this.weight = weight;
        this.size = size;
        this.registrationnumber = registrationnumber;
        this.servicearea = servicearea;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<String> getVehicaltype() {
        return vehicaltype;
    }

    public void setVehicaltype(List<String> vehicaltype) {
        this.vehicaltype = vehicaltype;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        this.registrationnumber = registrationnumber;
    }

    public List<String> getServicearea() {
        return servicearea;
    }

    public void setServicearea(List<String> servicearea) {
        this.servicearea = servicearea;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

/*
▪ Add personal details (name, phone, photo).
▪ Provide truck information:
▪ Vehicle type (mini-truck, container, tanker, etc.).
▪ Capacity (weight, dimensions).
▪ Registration number.
▪ Service area (cities/regions).
▪ Pricing details (per km or fixed rates).
▪ Update availability status (available/unavailable).
*/

