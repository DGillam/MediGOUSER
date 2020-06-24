package com.example.delivery.Model;

import java.security.PublicKey;
import java.util.ArrayList;

public class User {

    private String email;
    private String password;
    private String id;
    private String firstName;
    private String lastName;
    private String birthday;
    private ArrayList<String> medicineCurrent;
    private ArrayList<String> medicinePast;
    private Boolean firstLog;
    private String otp;
    private String publicKey;
    private String token;
    private String pastDelivery;
    private String nextDelivery;
    private String pharmacy;
    private String address;
    private ArrayList<String> deliveries;

    public User(String email, String password, String id,
                String firstName, String lastName, String birthday,
                ArrayList<String> medicineCurrent, ArrayList<String> medicinePast,
                Boolean firstLog, String otp, String publicKey, String token,
                String pastDelivery, String nextDelivery, String pharmacy, String address, ArrayList<String> deliveries) {

        this.email = email;
        this.password = password;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.medicineCurrent = medicineCurrent;
        this.medicinePast = medicinePast;
        this.firstLog = firstLog;
        this.otp = otp;
        this.publicKey = publicKey;
        this.token = token;
        this.pastDelivery = pastDelivery;
        this.nextDelivery = nextDelivery;
        this.pharmacy = pharmacy;
        this.address = address;
        this.deliveries = deliveries;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ArrayList<String> getMedicineCurrent() {
        return medicineCurrent;
    }

    public void setMedicineCurrent(ArrayList<String> medicineCurrent) {
        this.medicineCurrent = medicineCurrent;
    }

    public ArrayList<String> getMedicinePast() {
        return medicinePast;
    }

    public void setMedicinePast(ArrayList<String> medicinePast) {
        this.medicinePast = medicinePast;
    }

    public Boolean getFirstLog() {
        return firstLog;
    }

    public void setFirstLog(Boolean firstLog) {
        this.firstLog = firstLog;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPastDelivery() {
        return pastDelivery;
    }

    public void setPastDelivery(String pastDelivery) {
        this.pastDelivery = pastDelivery;
    }

    public String getNextDelivery() {
        return nextDelivery;
    }

    public void setNextDelivery(String nextDelivery) {
        this.nextDelivery = nextDelivery;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(ArrayList<String> deliveries) {
        this.deliveries = deliveries;
    }
}


