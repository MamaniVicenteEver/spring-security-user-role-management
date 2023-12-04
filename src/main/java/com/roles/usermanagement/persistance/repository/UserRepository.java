package com.roles.usermanagement.persistance.repository;

import com.roles.usermanagement.domain.dto.UserDto;
import com.roles.usermanagement.domain.repository.IUserRepository;
import com.roles.usermanagement.persistance.crud.UserCrudRepository;
import com.roles.usermanagement.persistance.entity.UserEntity;
import com.roles.usermanagement.persistance.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Clase que gestiona las operaciones específicas de base de datos para la entidad UserEntity.
 * Utiliza la interfaz UserCrudRepository para operaciones CRUD básicas.
 */
@Repository
public class UserRepository implements IUserRepository {

  private final UserCrudRepository userCrudRepository;
  private final UserMapper userMapper;

  @Autowired
  public UserRepository(UserCrudRepository userCrudRepository, UserMapper userMapper) {
    this.userCrudRepository = userCrudRepository;
    this.userMapper = userMapper;
  }

  /**
   * Carga un usuario por su nombre de usuario desde la base de datos.
   *
   * @param username El nombre de usuario del usuario que se busca.
   * @return La entidad UserEntity correspondiente al nombre de usuario proporcionado.
   */
  @Override
  public UserDto loadUserByUsername(String username) {
    UserEntity userEntity = userCrudRepository.findById(username).orElse(null);
    return userMapper.toUserDto(userEntity);
  }
}
