package com.gnut.bidscout.service.user;

import com.gnut.bidscout.db.UsersDao;
import com.gnut.bidscout.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    private final UsersDao repository;

    @Autowired
    public MongoUserDetailsService(UsersDao repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getId()));
        user.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getValue()));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
