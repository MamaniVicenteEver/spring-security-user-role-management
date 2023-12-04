package com.roles.usermanagement.domain.dto;

import lombok.Data;

@Data
public class UserRoleDto {
  public String username;
  public String role;
  public String grantedDate;
}
