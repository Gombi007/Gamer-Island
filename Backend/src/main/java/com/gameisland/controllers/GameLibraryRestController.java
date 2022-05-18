package com.gameisland.controllers;

import com.gameisland.services.GameLibraryService;
import com.gameisland.services.SteamApiDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "http://localhost:8080")
public class GameLibraryRestController {

    private final GameLibraryService gameLibraryService;
    private final SteamApiDetailService steamService;


    @Autowired
    public GameLibraryRestController(GameLibraryService gameLibraryService, SteamApiDetailService steamService) {
        this.gameLibraryService = gameLibraryService;
        this.steamService = steamService;
    }

    @GetMapping("/games")
    public ResponseEntity<Object> getAllGamesFromTheDatabase() {
        return ResponseEntity.status(HttpStatus.OK).body(gameLibraryService.getAllGamesFromDatabase());
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Object> getGameDetailsById(@PathVariable String id) {
        Long appId = Long.parseLong(id);
        return ResponseEntity.status(HttpStatus.OK).body(gameLibraryService.getGameDetailsByAppId(appId));
    }


}
