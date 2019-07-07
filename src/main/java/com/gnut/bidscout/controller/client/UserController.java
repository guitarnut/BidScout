package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.UserProfile;
import com.gnut.bidscout.model.Users;
import com.gnut.bidscout.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Users login(
            @RequestBody Users user,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.login(user, request, response);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ResponseStatus(value=HttpStatus.OK)
    public Users create(
            @RequestBody Users user,
            HttpServletResponse response
    ) {
        return service.createUser(user, response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/account/get", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public UserProfile get(
            Authentication auth
    ) {
        return service.getUser(auth);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/account/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void delete(
            Authentication auth
    ) {
        service.deleteUser(auth);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/account/update", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public UserProfile update(
            @RequestBody UserProfile user,
            Authentication auth
    ) {
        return service.updateUser(auth, user);
    }
}
