package com.gnut.bidscout.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Users {
    public enum Role {
        ADMIN("ADMIN"), USER("USER");

        private String value;

        Role(String v) {
            value = v;
        }

        public String getValue() {
            return value;
        }
    }

    @Id
    private ObjectId _id;
    private String username;
    private String password;
    private ArrayList<Role> roles;
    private boolean enabled;
    private Date created;
    private Date lastLogin;
    private String Address;
    private String City;
    private String State;
    private String zip;
    private String email;
    private String phone;
    private List<ArrayList> ipAccess;
    private List<ArrayList> uaAccess;
    private List<ArrayList> dateAccess;
    private int failedLoginAttemptCount;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
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

    public List<ArrayList> getIpAccess() {
        return ipAccess;
    }

    public void setIpAccess(List<ArrayList> ipAccess) {
        this.ipAccess = ipAccess;
    }

    public List<ArrayList> getUaAccess() {
        return uaAccess;
    }

    public void setUaAccess(List<ArrayList> uaAccess) {
        this.uaAccess = uaAccess;
    }

    public List<ArrayList> getDateAccess() {
        return dateAccess;
    }

    public void setDateAccess(List<ArrayList> dateAccess) {
        this.dateAccess = dateAccess;
    }

    public int getFailedLoginAttemptCount() {
        return failedLoginAttemptCount;
    }

    public void setFailedLoginAttemptCount(int failedLoginAttemptCount) {
        this.failedLoginAttemptCount = failedLoginAttemptCount;
    }
}
