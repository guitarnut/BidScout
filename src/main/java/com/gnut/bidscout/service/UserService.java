package com.gnut.bidscout.service;

import com.gnut.bidscout.db.UsersDao;
import com.gnut.bidscout.model.UserProfile;
import com.gnut.bidscout.model.Users;
import com.iab.openrtb.request.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
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

    public Users createUser(String id, Users user) {
        return user;
    }

    public void deleteUser(String id) {
    }

    public Users updateUser(String id, Users user) {
        return user;
    }

    public UserProfile getUser(String id, Users u) {
        Optional<Users> user =  usersDao.findById(id);
        // Todo: Verify user object with found user record
        return user.isPresent() ? new UserProfile(user.get()) : null;
    }
}
