package com.gnut.bidscout.model;

import java.util.List;

public class UserProfile {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private List<Users.Role> roles;
    private long lastLogin;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;

    public UserProfile() {
    }

    public UserProfile(Users u) {
        id = u.getId();
        username = u.getUsername();
        firstName = u.getFirstName();
        lastName = u.getLastName();
        roles = u.getRoles();
        lastLogin = u.getLastLogin();
        address = u.getAddress();
        city = u.getCity();
        state = u.getState();
        zip = u.getZip();
        email = u.getEmail();
        phone = u.getPhone();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Users.Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Users.Role> roles) {
        this.roles = roles;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
