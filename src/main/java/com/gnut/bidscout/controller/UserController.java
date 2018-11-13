package com.gnut.bidscout.controller;

import com.gnut.bidscout.model.UserProfile;
import com.gnut.bidscout.model.Users;
import com.gnut.bidscout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Users login(
            @RequestBody Users user
    ) {
        return service.login(user.getUsername());
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Users create(
            @RequestBody Users user,
            HttpServletResponse response
    ) {
        return service.createUser(user, response);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/account/get/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public UserProfile get(
            @PathVariable("id") String id,
            @RequestBody Users user,
            HttpServletResponse response
    ) {
        return service.getUser(id, user);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/account/delete/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        service.deleteUser(id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/account/update/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public UserProfile update(
            @PathVariable("id") String id,
            @RequestBody UserProfile user,
            HttpServletResponse response
    ) {
        return service.updateUser(id, user);
    }
}
