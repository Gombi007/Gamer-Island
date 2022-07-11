package com.gameisland.controllers;

import com.gameisland.models.dto.UserDTO;
import com.gameisland.models.entities.User;
import com.gameisland.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;


    @PostMapping("/registration")
    public ResponseEntity<Object> createANewUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createANewUser(user));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/username/{uuid}")
    public ResponseEntity<Object> getUsernameAndBalanceAndAvatarByUUID(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsernameAndBalanceAndAvatarByUUID(uuid));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cart/{uuid}")
    public ResponseEntity<Object> cartPurchase(@PathVariable String uuid, @RequestBody Long[] steamAppids) {
        userService.userCartPurchase(uuid, steamAppids);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/library/{uuid}")
    public ResponseEntity<Object> getLibraryDetails(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.libraryDetails(uuid));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile/{uuid}")
    public ResponseEntity<Object> getUserDataForProfile(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDataForProfile(uuid));
    }

    @PostMapping("/profile/update")
    public ResponseEntity<Object> updateUserData(@RequestBody UserDTO userDTO) {
        userService.updateUserData(userDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile/balance/{uuid}")
    public ResponseEntity<Object> updateUserBalance(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserBalance(uuid));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/wishlist/{uuid}")
    public ResponseEntity<Object> getUserWishlist(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserWishlist(uuid));
    }


}
