package com.gameisland.controllers;

import com.gameisland.services.SteamGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class GameRestController {
    private final SteamGameService gameService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shop")
    public ResponseEntity<Object> getAllGamesFromTheDatabaseForShop(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "1") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGamesFromDatabaseAndConvertDto(page, size));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameDetailsById(@PathVariable String id) {
        Long appId = Long.parseLong(id);
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameDetailsByAppId(appId));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/library")
    public ResponseEntity<Object> getLibraryDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.libraryDetails());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cart")
    public ResponseEntity<Object> getAllCartGames(@RequestBody Long[] steamAppIds) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllCartGames(steamAppIds));
    }


}
