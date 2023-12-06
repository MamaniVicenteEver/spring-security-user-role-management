package com.roles.usermanagement.persistance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la entidad de usuario en la base de datos.
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {

  @Id
  @Column(nullable = false, length = 50, unique = true)
  private String username;

  @Column(nullable = false, length = 200, unique = true)
  private String email;

  @Column(nullable = false, columnDefinition = "TINYINT")
  private Boolean locked;

  @Column(nullable = false, columnDefinition = "TINYINT")
  private Boolean disabled;

  @Column(nullable = false, length = 200)
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<UserRoleEntity> roles;
}