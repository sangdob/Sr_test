package com.SrBackend.service;

import com.SrBackend.entity.UserEntity;
import com.SrBackend.repository.UserRepository;
import com.SrBackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : {}", username);
        UserEntity user = userRepository.findByUsername(username).orElseGet(() ->
            userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("not found username:" + username)));
        return CustomUserDetails.of(user);
    }
}
