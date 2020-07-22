package com.epam.esm.service;

import com.epam.esm.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IRegistrationService extends UserDetailsService {

    UserDto register(UserDto userDto);

    ResponseEntity<?> signIn(AuthUserDto authUserDto);
}