package com.itengine.instagram.user.service;

import com.itengine.instagram.auth.model.RegistrationRequestDto;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public void create(RegistrationRequestDto registrationRequestDto) {
        User user = new User(registrationRequestDto.getUsername(), passwordEncoder.encode(registrationRequestDto.getPassword()), registrationRequestDto.getEmail());
        userRepository.save(user);
    }

    public void activate(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setActive(true);
        userRepository.save(user);
    }

    public boolean isActive(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        return user.isActive();
    }

    public boolean exists(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }
}
