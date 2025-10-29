package com.example.dto;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomerUser implements UserDetails {

	//사용자 DTO
    private Users user;

    public CustomerUser(Users user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthList()
                    .stream()
                    .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                    .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }
	
	
	
	
}
