package com.gameisland.controllers;

import com.gameisland.services.GameLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "http://localhost:8080")
public class GameLibraryRestController {

    private final GameLibraryService gameLibraryService;

    @Autowired
    public GameLibraryRestController(GameLibraryService gameLibraryService) {
        this.gameLibraryService = gameLibraryService;
    }

    @GetMapping("/games")
    public ResponseEntity<Object> getAllGameFromSteam() {
        return ResponseEntity.status(HttpStatus.OK).body(gameLibraryService.getAllGamesFromSteam());
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Object> getGameDetailsByAppIdFromSteam(@PathVariable String id) {
        Long appId = Long.parseLong(id);
        return ResponseEntity.status(HttpStatus.OK).body(gameLibraryService.getGameDetailsByAppIdFromSteam(appId));
    }

}
