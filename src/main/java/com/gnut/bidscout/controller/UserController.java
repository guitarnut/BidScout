package com.gnut.bidscout.controller;

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
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Users create(
            @PathVariable("id") String id,
            @RequestBody Users user,
            HttpServletResponse response
    ) {
        return service.createUser(id, user);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Users get(
            @PathVariable("id") String id,
            @RequestBody Users user,
            HttpServletResponse response
    ) {
        return service.getUser(id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        service.deleteUser(id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Users update(
            @PathVariable("id") String id,
            @RequestBody Users user,
            HttpServletResponse response
    ) {
        return service.updateUser(id, user);
    }
}
