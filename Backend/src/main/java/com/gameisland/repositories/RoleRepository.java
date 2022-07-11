package com.gameisland.repositories;

import com.gameisland.models.entities.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);

    @Override
    ArrayList<Role> findAll();

    @Modifying
    @Query(value = "DELETE FROM users_roles WHERE role_id = :roleId", nativeQuery = true)
    void deleteRoleEntriesFromUsersRolesTable(Long roleId);

}
