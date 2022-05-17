package com.gameisland.repositories;

import com.gameisland.models.entities.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    @Override
    ArrayList<Game> findAll();

    @Query(value = "SELECT steam_app_id FROM games", nativeQuery = true)
    ArrayList<Long> allExistingSteamAppId();
}
