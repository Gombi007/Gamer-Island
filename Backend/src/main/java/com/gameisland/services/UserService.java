package com.gameisland.services;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.dto.UserDTO;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, String> createUser(User user);

    User getUserByName(String username);

    Object getUsernameAndBalanceAndAvatarByUUID(String uuid);

    void userCartPurchase(String uuid, Long[] steamAppids);

    List<GameLibraryDetailsDto> libraryDetails(String uuid);

    UserDTO getUserDataForProfile(String uuid);

    void updateUserData(UserDTO userDTO);

    HashMap<String, String> updateUserBalance(String uuid);

    Object getUserWishlist(String uuid);

    Object addGamesToWishlist(String uuid, Long[] steamAppids);

    List<UserDTO> getAllUserFromDatabase();

    void removeAUserPermanently(String uuid);

    Role saveRole(Role role);

    void addRoleToUser(String uuid, String roleName);

    Map<String, String> removeRole(Map<String, String> roleName);

    List<Object> getAllRoles();

}
