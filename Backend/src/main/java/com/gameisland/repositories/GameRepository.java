package com.gameisland.repositories;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.entities.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    @Override
    ArrayList<Game> findAll();

    @Query(value = "SELECT steam_app_id FROM games", nativeQuery = true)
    Set<Long> allExistingSteamAppId();

    @Query(value = "SELECT * FROM games WHERE steam_app_id = :appId LIMIT 1", nativeQuery = true)
    Optional<Game> gameByAppId(Long appId);

    @Modifying
    @Query(value = "DELETE FROM user_game WHERE game_id = :gameId", nativeQuery = true)
    void deleteUserGameEntriesByGameId(Long gameId);

    @Query(nativeQuery = true)
    Set<GameLibraryDetailsDto> getLibraryDetails();
}
