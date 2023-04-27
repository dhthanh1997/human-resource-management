package com.ansv.humanresource.service;


import com.ansv.humanresource.dto.response.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface CustomUserDetailService extends UserDetailsService {

    UserDetails loadUser(String username, String displayName, String email) ;

    UserDetails loadUserByUsername(String username) ;

    UserDTO findByUsername(String username);

}
