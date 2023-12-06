package com.roles.usermanagement.web.controller;

import com.roles.usermanagement.domain.dto.UserDto;
import com.roles.usermanagement.domain.dto.UserRoleDto;
import com.roles.usermanagement.domain.service.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }


  /**
   Agregar el Try-Catch y en caso de error, registrar en mensaje por consola ( importar @Slf4j ) y devolver HTTP:500-InternalServerError
   En caso el Registro ya exista para Insertar devolver HTTP:409-CONFLICT, y en caso no Exista para Actualizar devolver HTTP:404-NOTFOUND
   Lo ideal seria estandarizar un objeto de retorno, para la salida de excepciones …, pero por el momento lo manejaremos directamente por Body el mensaje de ERROR
   */

  @DeleteMapping("/delete/{name}")
  public ResponseEntity<?> delete(@PathVariable String name) {
    try {
      if (this.userService.exists(name)) {
        this.userService.deleteUser(name);
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el usuario");
    } catch (Exception e) {
      logger.error("Error al eliminar el usuario", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/add")
  public ResponseEntity<?> save(@RequestBody UserDto userDto) {
    try {
      if (userDto.getUsername() == null || !this.userService.exists(userDto.getUsername())) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return ResponseEntity.ok(this.userService.saveUser(userDto));
      }
      return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
    } catch (Exception e) {
      logger.error("Error al guardar el usuario", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping("/update")
  public ResponseEntity<?> update(@RequestBody UserDto userDto) {
    try {
      if (userDto.getUsername() != null && this.userService.exists(userDto.getUsername())) {
        return ResponseEntity.ok(this.userService.saveUser(userDto));
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
    } catch (Exception e) {
      logger.error("Error al actualizar el usuario", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("/assignRole")
  public ResponseEntity<?> assignRole(@RequestBody UserRoleDto userRoleDto) {
    try {
      if (userRoleDto.getUsername() != null && this.userService.exists(userRoleDto.getUsername())) {

        return ResponseEntity.ok(this.userService.assignRoleToUser(userRoleDto));
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe");
    } catch (Exception e) {
      logger.error("Error al asignar el rol al usuario", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
