package com.itengine.instagram.user.service;

import com.itengine.instagram.auth.dto.RegistrationRequestDto;
import com.itengine.instagram.email.util.MailValidator;
import com.itengine.instagram.user.dto.UpdateDto;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.repository.UserRepository;
import com.itengine.instagram.user.util.LoggedUser;
import com.itengine.instagram.util.CredentialRegex;
import com.itengine.instagram.util.CredentialValidation;
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

    public User activate(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setActive(true);
        return userRepository.save(user);
    }

    public boolean isActive(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username);
        return user.isActive();
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public void updateAccount(UpdateDto updateDto) {
        if(!MailValidator.isValid(updateDto.getEmail())) {
            return;
        }

        if(!CredentialValidation.isPatternMatched(CredentialRegex.USERNAME_REGEX, updateDto.getUsername())) {
            return;
        }

        if(!CredentialValidation.isPatternMatched(CredentialRegex.PASSWORD_REGEX, updateDto.getPassword())) {
            return;
        }

        String username = LoggedUser.getUsername();
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setEmail(updateDto.getEmail());
        user.setUsername(updateDto.getUsername());
        user.setPassword(passwordEncoder.encode(updateDto.getPassword()));

        userRepository.save(user);
    }
}
