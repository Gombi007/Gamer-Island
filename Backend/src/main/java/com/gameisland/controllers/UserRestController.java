package com.gameisland.controllers;

import com.gameisland.models.dto.Login;
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

    @GetMapping
    public ResponseEntity<Object> getAllUserFromTheDatabase() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUserFromDatabase());
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Login login) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(login));
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> createANewUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createANewUser(user));
    }

    @GetMapping("/username/{uuid}")
    public ResponseEntity<Object> getUserNameByUUID(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserNameByUUID(uuid));
    }

    @DeleteMapping("/{userIdString}")
    public ResponseEntity<Object> deleteAUser(@PathVariable String userIdString) {
        Long userId = Long.parseLong(userIdString);
        userService.removeAUserPermanently(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
