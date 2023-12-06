package com.roles.usermanagement.persistance.mapper;

import com.roles.usermanagement.domain.dto.UserDto;
import com.roles.usermanagement.domain.dto.UserRoleDto;
import com.roles.usermanagement.persistance.entity.UserEntity;
import com.roles.usermanagement.persistance.entity.UserRoleEntity;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mappings({
     /* @Mapping(source = "username", target = "username"),
      @Mapping(source = "email", target = "email"),
      @Mapping(source = "locked", target = "locked"),
      @Mapping(source = "disabled", target = "disabled"),
      @Mapping(source = "password", target = "password")*/
  })
  UserDto toUserDto(UserEntity userEntity);

  List<UserDto> toUsersDto(List<UserEntity> userEntities);

  @InheritInverseConfiguration
  UserEntity toUserEntity(UserDto userDto);
}
