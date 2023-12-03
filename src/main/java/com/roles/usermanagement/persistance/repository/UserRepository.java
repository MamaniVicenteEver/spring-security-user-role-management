package com.roles.usermanagement.persistance.repository;

import com.roles.usermanagement.persistance.crud.UserCrudRepository;
import com.roles.usermanagement.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

  private final UserCrudRepository userCrudRepository;

  @Autowired
  public UserRepository(UserCrudRepository userCrudRepository) {
    this.userCrudRepository = userCrudRepository;
  }

  public UserEntity loadUserByUsername(String username) {
    return userCrudRepository.findById(username).orElse(null);
  }
}
