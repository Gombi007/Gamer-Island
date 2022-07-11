package com.gameisland.controllers;

import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.models.dto.RoleToUserFormDTO;
import com.gameisland.models.entities.Role;
import com.gameisland.services.SteamGameService;
import com.gameisland.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final SteamGameService gameService;
    private final UserService userService;

    //STEAM GAMES
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/steam/save-from-steam-to-file")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi() {
        gameService.saveProductsInAFileViaSteamApi();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/steam/save-from-file-to-db/{limit}")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi(@PathVariable(required = false) String limit) {
        if (limit == null) {
            limit = "300";
        }
        Integer queryLimit = Integer.parseInt(limit);
        gameService.saveSteamProductsFromFileDBToDatabase(queryLimit);
        log.warn("Games were saved");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // ROLES
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/all")
    public ResponseEntity<Object> getRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllRoles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/save")
    public ResponseEntity<Object> saveRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveRole(role));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/remove")
    public ResponseEntity<Object> removeRole(@RequestBody Map<String, String> roleName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.removeRole(roleName));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/add-to-user")
    public ResponseEntity<Object> addRoleToUser(@RequestBody RoleToUserFormDTO form) {
        userService.addRoleToUser(form.getUuid(), form.getRole());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // USERS AND GAMES
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/all")
    public ResponseEntity<Object> getAllUserFromTheDatabase() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUserFromDatabase());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{uuid}")
    public ResponseEntity<Object> deleteAUser(@PathVariable String uuid) {
        userService.removeAUserPermanently(uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/steam/{appid}")
    public ResponseEntity<Object> removeAGameFromTheDatabasePermanently(@PathVariable String appid) {
        Long gameAppid;
        try {
            gameAppid = Long.parseLong(appid);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("This is not a valid appid");
        }

        return ResponseEntity.status(HttpStatus.OK).body(gameService.removeAGamePermanentlyFromTheDatabaseByAppId(gameAppid));
    }


}



