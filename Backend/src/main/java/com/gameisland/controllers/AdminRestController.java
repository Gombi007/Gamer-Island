package com.gameisland.controllers;

import com.gameisland.services.SteamGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminRestController {
    private final SteamGameService gameService;

    @Autowired
    public AdminRestController(SteamGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/save-from-steam-to-file")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi() {
        gameService.saveProductsInAFileViaSteamApi();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/save-from-file-to-db/{limit}")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi(@PathVariable(required = false) String limit) {
        if (limit == null) {
            limit = "300";
        }
        Integer queryLimit = Integer.parseInt(limit);
        gameService.saveSteamProductsFromFileDBToDatabase(queryLimit);
        System.out.println("Games were saved");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeAGameFromTheDatabasePermanently(@PathVariable String id) {
        Long gameId = Long.parseLong(id);
        gameService.removeAGamePermanentlyFromTheDatabaseById(gameId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
