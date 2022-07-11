package com.gameisland.services;

import com.gameisland.models.dto.GameLibraryDetailsDto;
import com.gameisland.models.dto.UserDTO;
import com.gameisland.models.entities.Role;
import com.gameisland.models.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    Map<String, String> createANewUser(User user);

    Object getUsernameAndBalanceAndAvatarByUUID(String uuid);

    void removeAUserPermanently(String uuid);

    List<UserDTO> getAllUserFromDatabase();

    Role saveRole(Role role);

    Map<String, String> removeRole(Map<String, String> roleName);

    void addRoleToUser(String uuid, String roleName);

    List<Object> getAllRoles();

    User getUserByName(String username);

    void userCartPurchase(String uuid, Long[] steamAppids);

    Set<GameLibraryDetailsDto> libraryDetails(String uuid);

    Object getUserDataForProfile(String uuid);

    void updateUserData(UserDTO userDTO);

    HashMap<String, String> updateUserBalance(String uuid);

    Object getUserWishlist(String uuid);


}
