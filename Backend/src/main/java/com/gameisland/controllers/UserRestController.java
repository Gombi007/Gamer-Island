package com.gameisland.controllers;

import com.gameisland.models.entities.User;
import com.gameisland.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;


    @PostMapping("/registration")
    public ResponseEntity<Object> createANewUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createANewUser(user));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/username/{uuid}")
    public ResponseEntity<Object> getUserNameByUUID(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserNameByUUID(uuid));
    }


}
