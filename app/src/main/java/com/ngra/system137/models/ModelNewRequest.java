package com.ngra.system137.models;

import java.util.List;

public class ModelNewRequest {

    private List<String> Files;

    private String Name;

    private String Family;

    private String PhoneNuber;

    private Integer Type;

    private String Description;

    private String Address;

    private double Latitude;

    private double Longitude;


    public ModelNewRequest() {
    }


    public List<String> getFiles() {
        return Files;
    }

    public void setFiles(List<String> files) {
        Files = files;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }

    public String getPhoneNuber() {
        return PhoneNuber;
    }

    public void setPhoneNuber(String phoneNuber) {
        PhoneNuber = phoneNuber;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
