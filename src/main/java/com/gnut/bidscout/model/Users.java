package com.gnut.bidscout.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private boolean enabled;
    private long created;
    private long lastLogin;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;
    private List<String> ipAccess;
    private List<String> uaAccess;
    private List<Long> dateAccess;
    private int failedLoginAttemptCount;

    public void update(UserProfile newData) {
        username = newData.getUsername();
        firstName = newData.getFirstName();
        lastName = newData.getLastName();
        address = newData.getAddress();
        city = newData.getCity();
        state = newData.getState();
        zip = newData.getZip();
        email = newData.getEmail();
        phone = newData.getPhone();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
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

    public List<String> getIpAccess() {
        return ipAccess;
    }

    public void setIpAccess(List<String> ipAccess) {
        this.ipAccess = ipAccess;
    }

    public List<String> getUaAccess() {
        return uaAccess;
    }

    public void setUaAccess(List<String> uaAccess) {
        this.uaAccess = uaAccess;
    }

    public List<Long> getDateAccess() {
        return dateAccess;
    }

    public void setDateAccess(List<Long> dateAccess) {
        this.dateAccess = dateAccess;
    }

    public int getFailedLoginAttemptCount() {
        return failedLoginAttemptCount;
    }

    public void setFailedLoginAttemptCount(int failedLoginAttemptCount) {
        this.failedLoginAttemptCount = failedLoginAttemptCount;
    }
}
