package com.roles.usermanagement.persistance.crud;

import com.roles.usermanagement.persistance.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleCrudRepository extends CrudRepository<UserRoleEntity, String> {

}
