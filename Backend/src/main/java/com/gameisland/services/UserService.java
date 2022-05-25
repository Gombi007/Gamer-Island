package com.gameisland.services;

import com.gameisland.models.entities.User;

public interface UserService {

    User createANewUser(User user);

    User addAGameToUser(Long userId, Long gameID);

}
