package com.roles.usermanagement.domain.service;

import com.roles.usermanagement.domain.dto.UserDto;
import com.roles.usermanagement.domain.dto.UserRoleDto;
import com.roles.usermanagement.persistance.entity.UserEntity;
import com.roles.usermanagement.persistance.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.roles.usermanagement.persistance.entity.UserRoleEntity;

/**
 * Servicio que implementa la interfaz UserDetailsService para la autenticación y autorización de usuarios.
 * Carga los detalles del usuario desde la base de datos y proporciona los datos necesarios para la seguridad.
 */
@Service
public class UserSecurityService  implements UserDetailsService {

  private final UserRepository userRepository;
  public static final String RANDOM_ORDER = "random_order";
  public static final String ROLE_PREFIX = "ROLE_";
  @Autowired
  public UserSecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Carga los detalles de un usuario por su nombre de usuario.
   *
   * @param username Nombre de usuario del usuario que se busca.
   * @return Detalles del usuario para la autenticación y autorización.
   * @throws UsernameNotFoundException Si el usuario no es encontrado en la base de datos.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto userEntity = this.userRepository.loadUserByUsername(username);

    if (userEntity == null) {
      throw new UsernameNotFoundException("User " + username + " not found.");
    }

    String[] roles = userEntity.getRoles().stream().map(UserRoleDto::getRole).toArray(String[]::new);

    return User.builder()
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .authorities(this.grantedAuthorities(roles))
        .accountLocked(userEntity.getLocked())
        .disabled(userEntity.getDisabled())
        .build();
  }

  /**
   * Obtiene las autoridades asociadas a un rol determinado.
   *
   * @param role El rol del usuario.
   * @return Un array de cadenas representando las autoridades del rol.
   */
  private String[] getAuthorities(String role) {
    if (UserRoles.Role.ADMIN.name().equals(role) || UserRoles.Role.CUSTOMER.name().equals(role)) {
      return new String[] {RANDOM_ORDER};
    }

    return new String[] {};
  }

  /**
   * Genera la lista de autoridades para un conjunto de roles.
   *
   * @param roles Los roles del usuario.
   * @return Una lista de autoridades otorgadas a los roles.
   */
  private List<GrantedAuthority> grantedAuthorities(String[] roles) {
    List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

    for (String role: roles) {
      authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

      for (String authority: this.getAuthorities(role)) {
        authorities.add(new SimpleGrantedAuthority(authority));
      }
    }

    return authorities;
  }
}