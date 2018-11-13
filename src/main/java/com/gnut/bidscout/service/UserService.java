package com.gnut.bidscout.service;

import com.gnut.bidscout.db.UsersDao;
import com.gnut.bidscout.model.UserProfile;
import com.gnut.bidscout.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UserService {
    private final UsersDao usersDao;

    @Autowired
    public UserService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public Users login(String username) {
        return usersDao.findByUsername(username);
    }

    public Users createUser(Users user, HttpServletResponse response) {
        if(usersDao.findByUsername(user.getUsername()) != null) {
            response.setStatus(204);
            return null;
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRoles(Arrays.asList(Users.Role.ADMIN, Users.Role.USER));
            return usersDao.save(user);
        }
    }

    public void deleteUser(String id) {
    }

    public UserProfile updateUser(String id, UserProfile updatedUser) {
        Optional<Users> user = usersDao.findById(id);
        if(user.isPresent()) {
            user.get().update(updatedUser);
            usersDao.save(user.get());
        }
        return updatedUser;
    }

    public UserProfile getUser(String id, Users u) {
        Optional<Users> user =  usersDao.findById(id);
        // Todo: Verify user object with found user record
        return user.isPresent() ? new UserProfile(user.get()) : null;
    }
}
