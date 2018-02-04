package com.bookcatalog.registration.repositories;

import com.bookcatalog.registration.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
