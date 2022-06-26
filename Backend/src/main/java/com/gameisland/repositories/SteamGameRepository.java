package com.gameisland.repositories;

import com.gameisland.models.entities.SteamGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SteamGameRepository extends JpaRepository<SteamGame, Long> {
    @Override
    ArrayList<SteamGame> findAll();

    @Query(value = "SELECT steam_app_id FROM steam_games", nativeQuery = true)
    Set<Long> allExistingSteamAppId();

    @Query(value = "SELECT * FROM steam_games WHERE steam_app_id = :appId LIMIT 1", nativeQuery = true)
    Optional<SteamGame> gameByAppId(Long appId);

    @Modifying
    @Query(value = "DELETE FROM user_game WHERE game_id = :gameId", nativeQuery = true)
    void deleteUserGameEntriesByGameId(Long gameId);

}
