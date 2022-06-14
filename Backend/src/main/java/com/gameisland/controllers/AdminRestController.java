package com.gameisland.controllers;

import com.gameisland.models.entities.Role;
import com.gameisland.services.SteamGameService;
import com.gameisland.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class AdminRestController {
    private final SteamGameService gameService;
    private final UserService userService;

    @GetMapping("/steam/save-from-steam-to-file")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi() {
        gameService.saveProductsInAFileViaSteamApi();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/steam/save-from-file-to-db/{limit}")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi(@PathVariable(required = false) String limit) {
        if (limit == null) {
            limit = "300";
        }
        Integer queryLimit = Integer.parseInt(limit);
        gameService.saveSteamProductsFromFileDBToDatabase(queryLimit);
        System.out.println("Games were saved");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/steam/{id}")
    public ResponseEntity<Object> removeAGameFromTheDatabasePermanently(@PathVariable String id) {
        Long gameId = Long.parseLong(id);
        gameService.removeAGamePermanentlyFromTheDatabaseById(gameId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/roles/save")
    public ResponseEntity<Object> saveRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveRole(role));
    }

    @PostMapping("/roles/add-to-user")
    public ResponseEntity<Object> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUuid(), form.getRole());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

@Data
class RoleToUserForm {
    private String uuid;
    private String role;
}

