package com.gameisland.controllers;

import com.gameisland.services.GameLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminRestController {
    private final GameLibraryService gameLibraryService;

    @Autowired
    public AdminRestController(GameLibraryService gameLibraryService) {
        this.gameLibraryService = gameLibraryService;
    }

    @GetMapping("/save-from-steam-to-file")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi() {
        gameLibraryService.saveProductsInAFileViaSteamApi();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/save-from-file-to-db/{limit}")
    public ResponseEntity<Object> saveProductsInAFileViaSteamApi(@PathVariable String limit) {
        Integer queryLimit = Integer.parseInt(limit);
        gameLibraryService.saveSteamProductsFromFileDBToDatabase(queryLimit);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeAGameFromTheDatabasePermanently(@PathVariable String id) {
        Long gameId = Long.parseLong(id);
        gameLibraryService.removeAGamePermanentlyFromTheDatabaseById(gameId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
