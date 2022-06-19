package com.gameisland.repositories;

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

    @Query(value = "SELECT count(user_name) = 1 FROM users WHERE user_name = :userName", nativeQuery = true)
    boolean findExistByName(String userName);

    @Query(value = "SELECT count(user_name) = 1 FROM users WHERE user_name = :userName AND password = :password", nativeQuery = true)
    boolean loginDataValidate(String userName, String password);

    @Query(value = "SELECT password FROM users WHERE user_name = :userName", nativeQuery = true)
    String getPasswordHash(String userName);

    @Query(value = "SELECT useruuid FROM users WHERE user_name = :userName", nativeQuery = true)
    String getUserUUID(String userName);

    @Query(value = "SELECT * FROM users WHERE useruuid = :UUID", nativeQuery = true)
    Optional<User> getUserByUUID(String UUID);

    @Query(value = "SELECT user_name, balance FROM users WHERE useruuid = :UUID", nativeQuery = true)
    String getUsernameAndBalance(String UUID);

    @Query(value = "SELECT useruuid FROM users", nativeQuery = true)
    Set<String> getAllExistingUUID();


    @Modifying
    @Query(value = "DELETE FROM user_game WHERE user_id = :userId", nativeQuery = true)
    void deleteUserGameEntriesByUserId(Long userId);

    User findByUserName(String username);
}
