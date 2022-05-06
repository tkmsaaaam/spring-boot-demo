package com.example.springboot;

import java.util.Collection;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;

public interface UserDetails extends Serializable {
  Collection<? extends GrantedAuthority> getAuthorities();
  String getPasswrod();
  String getUsername();
  boolean isAccountNonExpired();
  boolean isCredentialsNonExpired();
  boolean isEnabled();
}
