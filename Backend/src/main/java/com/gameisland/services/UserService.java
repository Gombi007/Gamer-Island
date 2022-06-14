package com.gameisland.services;

import com.gameisland.models.dto.Login;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;

import java.util.ArrayList;

public interface UserService {

    User createANewUser(User user);

    Object getUserNameByUUID(String uuid);

    void removeAUserPermanently(String uuid);

    ArrayList<User> getAllUserFromDatabase();

    Role saveRole(Role role);

    void addRoleToUser(String uuid, String roleName);

    ArrayList<Role> getAllRoles();

    User getUserByName(String username);

}
