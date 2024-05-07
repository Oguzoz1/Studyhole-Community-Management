package com.studyhole.app.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studyhole.app.model.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImplementor implements UserDetailsService{
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userService.fetchUserOptional(username).get();
         return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
         user.isEnabled(),true,true,true, getAuthorities("USER"));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(String role){
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
