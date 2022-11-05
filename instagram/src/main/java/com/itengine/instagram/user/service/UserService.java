package com.itengine.instagram.user.service;

import com.itengine.instagram.auth.dto.RegistrationRequestDto;
import com.itengine.instagram.email.util.MailValidator;
import com.itengine.instagram.follow.model.Follow;
import com.itengine.instagram.follow.service.FollowService;
import com.itengine.instagram.post.util.PostConverter;
import com.itengine.instagram.user.dto.UserFollowDto;
import com.itengine.instagram.user.dto.UserResponseDto;
import com.itengine.instagram.user.dto.UserUpdateDto;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.repository.UserRepository;
import com.itengine.instagram.user.util.LoggedUser;
import com.itengine.instagram.user.util.UserConverter;
import com.itengine.instagram.util.CredentialRegex;
import com.itengine.instagram.util.CredentialValidation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostConverter postConverter;
    private final UserConverter userConverter;
    private final FollowService followService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostConverter postConverter, UserConverter userConverter, FollowService followService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postConverter = postConverter;
        this.userConverter = userConverter;
        this.followService = followService;
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

    public void updateAccount(UserUpdateDto userUpdateDto) {
        if (!MailValidator.isValid(userUpdateDto.getEmail())) {
            return;
        }

        if (!CredentialValidation.isPatternMatched(CredentialRegex.USERNAME_REGEX, userUpdateDto.getUsername())) {
            return;
        }

        if (!CredentialValidation.isPatternMatched(CredentialRegex.PASSWORD_REGEX, userUpdateDto.getPassword())) {
            return;
        }

        String username = LoggedUser.getUsername();
        User user = userRepository.findByUsernameIgnoreCase(username);
        user.setEmail(userUpdateDto.getEmail());
        user.setUsername(userUpdateDto.getUsername());
        user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));

        userRepository.save(user);
    }

    public void delete() {
        Long id = LoggedUser.getId();
        userRepository.deleteById(id);
    }

    public List<User> getFollowedUsers(Long userId) {
        User user = userRepository.getById(userId);

        List<User> followedUsers = new ArrayList<>();

        for (Follow follow : user.getFollowing()) {
            followedUsers.add(follow.getFollowTo());
        }

        return followedUsers;
    }

    public List<UserFollowDto> getFollowedDtoUsers(Long userId) {
        return userConverter.convertToUserFollowerDtos(getFollowedUsers(userId));
    }

    public UserResponseDto getProfile(Long userId) {
        User user = userRepository.getById(userId);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setImage(user.getImage());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setDescription(user.getDescription());
        userResponseDto.setPosts(postConverter.convertToPostDtos(user.getPosts()));
        userResponseDto.setNumberOfFollowing(user.getFollowing().size());
        userResponseDto.setNumberOfFollowers(user.getFollowers().size());

        return userResponseDto;
    }

    public List<UserFollowDto> getFollowers(Long userId) {
        User user = userRepository.getById(userId);
        List<User> following = new ArrayList<>();

        for (Follow follow : user.getFollowers()) {
            following.add(follow.getFollowFrom());
        }

        return userConverter.convertToUserFollowerDtos(following);
    }

    public void follow(Long userId) {
        User userToFollow = userRepository.getById(userId);
        followService.createFollow(LoggedUser.getUser(), userToFollow);
    }

    public void unfollow(Long userId) {
        User userToUnfollow = userRepository.getById(userId);
        followService.removeFollow(LoggedUser.getUser(), userToUnfollow);
    }
}
