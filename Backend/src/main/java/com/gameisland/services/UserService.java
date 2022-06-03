package com.gameisland.services;

import com.gameisland.models.dto.Login;
import com.gameisland.models.entities.User;

import java.util.ArrayList;

public interface UserService {

    User createANewUser(User user);

    User getUserByUUID(String uuid);

    User addAGameToUser(Long userId, Long gameID);

    void removeAUserPermanently(Long userId);

    ArrayList<User> getAllUserFromDatabase();

    Object login(Login login);

}
