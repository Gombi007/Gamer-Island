package com.gameisland.services;

import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserService {

    Map<String, String> createANewUser(User user);

    Object getUserNameByUUID(String uuid);

    void removeAUserPermanently(String uuid);

    ArrayList<User> getAllUserFromDatabase();

    Role saveRole(Role role);

    void addRoleToUser(String uuid, String roleName);

    ArrayList<Role> getAllRoles();

    User getUserByName(String username);

}
