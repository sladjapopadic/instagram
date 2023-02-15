package com.itengine.instagram.user.service;

import com.itengine.instagram.auth.dto.RegistrationRequestDto;
import com.itengine.instagram.follow.service.FollowService;
import com.itengine.instagram.image.ImageService;
import com.itengine.instagram.post.util.PostConverter;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.repository.UserRepository;
import com.itengine.instagram.util.util.UserConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PostConverter postConverter;
    @Mock
    private UserConverter userConverter;
    @Mock
    private FollowService followService;
    @Mock
    private ImageService imageService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private UserService userService;

    private static final String USERNAME = "testUsername";
    public static final String PASSWORD = "testPassword";
    public static final String ENCODED_PASSWORD = "encodedTestPassword";
    public static final String EMAIL = "test@mail.com";

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void existsByUsername(boolean existsInRepository) {

        when(userRepository.existsByUsernameIgnoreCase(USERNAME)).thenReturn(existsInRepository);

        boolean actual = userService.existsByUsername(USERNAME);

        assertEquals(existsInRepository, actual);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void existsByEmail(boolean existsInRepository) {

        when(userRepository.existsByEmailIgnoreCase(EMAIL)).thenReturn(existsInRepository);

        boolean actual = userService.existsByEmail(EMAIL);

        assertEquals(existsInRepository, actual);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isActive(boolean isActiveUser) {
        User user = new User();
        user.setActive(isActiveUser);

        when(userRepository.findByUsernameIgnoreCase(USERNAME)).thenReturn(user);

        boolean actual = userService.isActive(USERNAME);

        assertEquals(isActiveUser, actual);
    }

    @Test
    void activate() {
        User user = new User();
        User savedUser = new User();

        when(userRepository.findByUsernameIgnoreCase(USERNAME)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);

        User actual = userService.activate(USERNAME);

        assertTrue(user.isActive());
        assertEquals(savedUser, actual);
    }

    @Test
    void create() throws IOException {
        RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
        registrationRequestDto.setUsername(USERNAME);
        registrationRequestDto.setPassword(PASSWORD);
        registrationRequestDto.setEmail(EMAIL);
        byte[] defaultImage = new byte[]{};

        when(imageService.getDefaultProfileImage()).thenReturn(defaultImage);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);

        userService.create(registrationRequestDto);

        verify(userRepository).save(userCaptor.capture());

        User user = userCaptor.getValue();

        assertEquals(USERNAME, user.getUsername());
        assertEquals(ENCODED_PASSWORD, user.getPassword());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(defaultImage, user.getImage());
    }
}
