package com.gameisland.controllers;

import com.gameisland.services.SteamGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameRestController {
    private final SteamGameService gameService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameDetailsById(@PathVariable String id) {
        Long appId = Long.parseLong(id);
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameDetailsByAppId(appId));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cart")
    public ResponseEntity<Object> getAllCartGames(@RequestBody Long[] steamAppIds) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllCartGames(steamAppIds));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/genres")
    public ResponseEntity<Object> getAllGenres() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getAllGenres());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shop/filter")
    public ResponseEntity<Object> getGamesByAttributeFilter(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "1") int size,
                                                            @RequestParam(name = "attribute", defaultValue = "") String attribute,
                                                            @RequestParam(name = "attributeValue", defaultValue = "") String attributeValue) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getGamesByNameOrGenreOrDescriptionAndConvertDto(page, size, attribute, attributeValue));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shop/min-max-price")
    public ResponseEntity<Object> getMinAndMaxPrice() {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.getMinAndMaxPrice());
    }


}
