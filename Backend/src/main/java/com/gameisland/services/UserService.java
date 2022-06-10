package com.gameisland.services;

import com.gameisland.models.dto.Login;
import com.gameisland.models.entities.User;

import java.util.ArrayList;

public interface UserService {

    User createANewUser(User user);

    Object getUserNameByUUID(String uuid);

    void removeAUserPermanently(Long userId);

    ArrayList<User> getAllUserFromDatabase();

    Object login(Login login);

}
