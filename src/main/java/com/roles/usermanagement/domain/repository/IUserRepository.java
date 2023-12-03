package com.roles.usermanagement.domain.repository;

import com.roles.usermanagement.persistance.entity.UserEntity;

/**
 * Interfaz que define las operaciones para acceder a usuarios en el dominio.
 */
public interface IUserRepository {

  /**
   * Carga un usuario por su nombre de usuario.
   *
   * @param username El nombre de usuario del usuario que se busca.
   * @return La entidad UserEntity correspondiente al nombre de usuario proporcionado.
   */
  UserEntity loadUserByUsername(String username);
}