package com.gameisland.repositories;

import com.gameisland.models.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    ArrayList<User> findAll();

    @Modifying
    @Query(value = "DELETE FROM user_game WHERE user_id = :userId", nativeQuery = true)
    void deleteUserGameEntriesByUserId(Long userId);

}
