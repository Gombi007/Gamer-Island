package com.gameisland.repositories;

import com.gameisland.models.entities.GameStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GameStatRepository extends CrudRepository<GameStat, Long> {

    @Override
    ArrayList<GameStat> findAll();

}
