package com.gnut.bidscout.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Users {
    public enum Role {
        ADMIN("ADMIN"), USER("USER");

        private String value;

        Role(String v) {
            value = v;
        }

        public String getValue(){
            return value;
        }
    }

    @Id
    public ObjectId _id;
    public String username;
    public String password;
    public Role role;

    public Users(ObjectId _id, String username, String password) {
        this._id = _id;
        this.username = username;
        this.password = password;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
