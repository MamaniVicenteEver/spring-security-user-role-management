package com.roles.usermanagement.persistance.crud;

import com.roles.usermanagement.persistance.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Interfaz que define operaciones CRUD (Crear, Leer, Actualizar, Borrar) para la entidad UserEntity.
 * Extiende CrudRepository proporcionado por Spring Data JPA.
 *
 * Permite realizar operaciones básicas de persistencia para objetos UserEntity en la base de datos.
 */
public interface UserCrudRepository extends CrudRepository<UserEntity, String> {

}
