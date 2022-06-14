package com.gameisland.repositories;

import com.gameisland.models.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);

    @Override
    ArrayList<Role> findAll();

}
