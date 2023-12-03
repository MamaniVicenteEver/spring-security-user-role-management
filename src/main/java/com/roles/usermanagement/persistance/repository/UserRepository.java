package com.roles.usermanagement.persistance.repository;

import com.roles.usermanagement.domain.repository.IUserRepository;
import com.roles.usermanagement.persistance.crud.UserCrudRepository;
import com.roles.usermanagement.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Clase que gestiona las operaciones específicas de base de datos para la entidad UserEntity.
 * Utiliza la interfaz UserCrudRepository para operaciones CRUD básicas.
 */
@Repository
public class UserRepository implements IUserRepository {

  private final UserCrudRepository userCrudRepository;

  @Autowired
  public UserRepository(UserCrudRepository userCrudRepository) {
    this.userCrudRepository = userCrudRepository;
  }

  /**
   * Carga un usuario por su nombre de usuario desde la base de datos.
   *
   * @param username El nombre de usuario del usuario que se busca.
   * @return La entidad UserEntity correspondiente al nombre de usuario proporcionado.
   */
  @Override
  public UserEntity loadUserByUsername(String username) {
    return userCrudRepository.findById(username).orElse(null);
  }
}
