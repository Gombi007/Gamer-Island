package com.gameisland.controllers;

import com.gameisland.services.GameLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> getAllGame() {
        return ResponseEntity.status(HttpStatus.OK).body(gameLibraryService.getAllGames());
    }

}
