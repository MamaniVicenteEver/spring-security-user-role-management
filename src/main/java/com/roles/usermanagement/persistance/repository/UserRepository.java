package com.roles.usermanagement.persistance.repository;

import com.roles.usermanagement.domain.dto.UserDto;
import com.roles.usermanagement.domain.dto.UserRoleDto;
import com.roles.usermanagement.domain.repository.IUserRepository;
import com.roles.usermanagement.persistance.crud.UserCrudRepository;
import com.roles.usermanagement.persistance.entity.UserEntity;
import com.roles.usermanagement.persistance.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
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

  /**
   * Crear o actualizar un usuario en la base de datos.
   *
   * @param userDto La entidad UserEntity que se desea crear o actualizar.
   *                   Si el nombre de usuario ya existe, se actualiza.
   *                   Si el nombre de usuario no existe, se crea.
   */
  @Override
  public UserDto save(UserDto userDto) {
    UserEntity user = userMapper.toUserEntity(userDto);
    return userMapper.toUserDto(userCrudRepository.save(user));
  }

  /**
   * Obtiene todos los usuarios de la base de datos.
   * @return Lista de usuarios.
   */
  @Override
  public List<UserDto> getAllUsers() {
    List<UserEntity> users = (List<UserEntity>) userCrudRepository.findAll();
    return userMapper.toUsersDto(users);
  }

  /**
   * Elimina un usuario de la base de datos.
   * @param username El nombre de usuario del usuario que se desea eliminar.
   */
  @Override
  public void deleteUser(String username) {
    userCrudRepository.deleteById(username);
  }

  /**
   * Si existe un usuario con el nombre de usuario proporcionado.
   * @param username El nombre de usuario del usuario que se desea buscar.
   */
  @Override
  public boolean existsByUsername(String username) {
    return userCrudRepository.existsById(username);
  }
}
