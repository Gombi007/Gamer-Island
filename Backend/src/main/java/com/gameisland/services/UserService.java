package com.gameisland.services;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.dto.UserDTO;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface UserService {

    Map<String, String> createANewUser(User user);

    Object getUsernameAndBalanceAndAvatarByUUID(String uuid);

    void removeAUserPermanently(String uuid);

    ArrayList<User> getAllUserFromDatabase();

    Role saveRole(Role role);

    void addRoleToUser(String uuid, String roleName);

    ArrayList<Role> getAllRoles();

    User getUserByName(String username);

    void userCartPurchase(String uuid, Long[] steamAppids);

    Set<GameLibraryDetailsDto> libraryDetails(String uuid);

    Object getUserDataForProfile(String uuid);

    void updateUserData(UserDTO userDTO);


}
