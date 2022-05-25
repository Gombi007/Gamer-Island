package com.gameisland.controllers;

import com.gameisland.models.entities.User;
import com.gameisland.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Object> createANewUser(@RequestBody User user) {
        userService.createANewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
