package com.roles.usermanagement.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la relación entre usuarios y roles en la base de datos.
 */
@Getter
@Setter
@Entity
@IdClass(UserRoleId.class)
@Table(name = "user_role")
public class UserRoleEntity {

  @Id
  @Column(nullable = false, length = 50, unique = true)
  private String username;

  @Id
  @Column(nullable = false, length = 50)
  private String role;

  @Column(nullable = false, length = 200,  columnDefinition = "DATETIME", name = "granted_date")
  private LocalDateTime grantedDate;

  @ManyToOne
  @JoinColumn(name = "username", insertable = false, updatable = false)
  @JsonIgnore
  private UserEntity user;
}
