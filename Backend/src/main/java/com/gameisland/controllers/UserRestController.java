package com.gameisland.controllers;

import com.gameisland.exceptions.ResourceNotFoundException;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/username/{uuid}")
    public ResponseEntity<Object> getUsernameAndBalanceAndAvatarByUUID(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsernameAndBalanceAndAvatarByUUID(uuid));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/wishlist-library/{uuid}/{steamAppid}")
    public ResponseEntity<Object> isUserOwnTheGameOrOnTheWishlist(@PathVariable String uuid, @PathVariable String steamAppid) {
        Long steamAppidNumber = null;
        try {
            steamAppidNumber = Long.parseLong(steamAppid);
        } catch (NumberFormatException exception) {
            throw new ResourceNotFoundException("Please add a number instead of this: " + steamAppid);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.isUserOwnTheGameOrOnTheWishlist(uuid, steamAppidNumber));
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

    //wishlist
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/wishlist/{uuid}")
    public ResponseEntity<Object> getUserWishlist(@PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserWishlist(uuid));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/wishlist/{uuid}")
    public ResponseEntity<Object> addGamesToWishlist(@PathVariable String uuid, @RequestBody Long[] steamAppids) {
        userService.addGamesToWishlist(uuid, steamAppids);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/wishlist/{uuid}")
    public ResponseEntity<Object> removeGameFromWishlist(@PathVariable String uuid, @RequestBody Long[] steamAppids) {
        System.out.println(steamAppids[0]);
        userService.removeGameFromWishlist(uuid, steamAppids);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
