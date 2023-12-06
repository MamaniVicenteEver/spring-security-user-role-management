package com.roles.usermanagement.persistance.repository;

import com.roles.usermanagement.domain.dto.UserRoleDto;
import com.roles.usermanagement.domain.repository.IUserRoleRepository;
import com.roles.usermanagement.persistance.crud.UserRoleCrudRepository;
import com.roles.usermanagement.persistance.entity.UserRoleEntity;
import com.roles.usermanagement.persistance.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepository implements IUserRoleRepository {
  public final UserRoleCrudRepository userRoleCrudRepository;
  public final UserRoleMapper userRoleMapper;

  @Autowired
  public UserRoleRepository(UserRoleCrudRepository userRoleCrudRepository,
      UserRoleMapper userRoleMapper) {
    this.userRoleCrudRepository = userRoleCrudRepository;
    this.userRoleMapper = userRoleMapper;
  }

  @Override
  public UserRoleDto save(UserRoleDto userRoleDto) {
    UserRoleEntity userRoleEntity = userRoleMapper.toUserRoleEntity(userRoleDto);
    return userRoleMapper.toUserRoleDto(userRoleCrudRepository.save(userRoleEntity));
  }

}
