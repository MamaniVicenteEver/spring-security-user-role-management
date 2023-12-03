package com.roles.usermanagement.domain.service;

import com.roles.usermanagement.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

  private final UserRepository userRepository;

  @Autowired
  public UserSecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
