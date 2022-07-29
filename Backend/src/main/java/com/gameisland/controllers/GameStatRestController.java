package com.gameisland.controllers;

import com.gameisland.exceptions.ResourceNotFoundException;
import com.gameisland.services.GameStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-stats")
@RequiredArgsConstructor
public class GameStatRestController {
    private final GameStatService gameStatService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{uuid}/{steamAppIdString}")
    public ResponseEntity<Object> getGameStat(@PathVariable String uuid, @PathVariable String steamAppIdString) {
        Long steamAppId = null;
        try {
            steamAppId = Long.parseLong(steamAppIdString);
        } catch (NumberFormatException exception) {
            throw new ResourceNotFoundException("Please add a number instead of this: " + steamAppIdString);
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameStatService.getGameStat(uuid, steamAppId));
    }


}
