package com.gameisland.repositories;

import com.gameisland.models.entities.GamePrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePriceRepository extends CrudRepository<GamePrice, Long> {
}
