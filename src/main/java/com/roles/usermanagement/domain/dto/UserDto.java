package com.roles.usermanagement.domain.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDto {
  private String username;
  private String email;
  private Boolean locked;
  private Boolean disabled;
  private String password;
  List<UserRoleDto> roles;
}
