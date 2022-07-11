package com.gameisland.repositories;

import com.gameisland.models.dto.UserDTO;
import com.gameisland.models.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    ArrayList<User> findAll();

    @Query(value = "SELECT * FROM users WHERE useruuid = :UUID", nativeQuery = true)
    Optional<User> getUserByUUID(String UUID);

    @Query(value = "SELECT user_name, balance, avatar FROM users WHERE useruuid = :UUID", nativeQuery = true)
    String getUsernameAndBalanceAndAvatar(String UUID);

    @Query(value = "SELECT useruuid FROM users", nativeQuery = true)
    Set<String> getAllExistingUUID();

    @Modifying
    @Query(value = "DELETE FROM users_games WHERE user_id = :userId", nativeQuery = true)
    void deleteUserGameEntriesByUserId(Long userId);

    User findByUserName(String username);

    @Query(nativeQuery = true)
    UserDTO findUserDTOByUserUUID(String UUID);

}
