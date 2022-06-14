package com.gameisland.controllers;

import com.gameisland.services.SteamGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class GameRestController {
    private final SteamGameService gameService;

    @GetMapping("/shop")
    public ResponseEntity<Object> getAllGamesFromTheDatabaseForShop(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "1") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGamesFromDatabaseAndConvertDto(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameDetailsById(@PathVariable String id) {
        Long appId = Long.parseLong(id);
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameDetailsByAppId(appId));
    }

    @GetMapping("/library")
    public ResponseEntity<Object> getLibraryDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.libraryDetails());
    }

}
