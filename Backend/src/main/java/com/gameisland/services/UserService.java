package com.gameisland.services;

import com.gameisland.models.entities.User;

import java.util.ArrayList;

public interface UserService {

    User createANewUser(User user);

    User addAGameToUser(Long userId, Long gameID);

    void removeAUserPermanently(Long userId);

    ArrayList<User> getAllUserFromDatabase();

}
