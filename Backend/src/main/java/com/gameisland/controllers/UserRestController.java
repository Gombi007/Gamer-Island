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

    @PostMapping("/{userIdString}/games/{gameIdString}")
    public ResponseEntity<Object> addAGameToUser(@PathVariable String userIdString, @PathVariable String gameIdString) {
        Long userId = Long.parseLong(userIdString);
        Long gameId = Long.parseLong(gameIdString);

        return ResponseEntity.status(HttpStatus.OK).body(userService.addAGameToUser(userId, gameId));
    }

    @DeleteMapping("/{userIdString}")
    public ResponseEntity<Object> deleteAUser(@PathVariable String userIdString) {
        Long userId = Long.parseLong(userIdString);
        userService.removeAUserPermanently(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
