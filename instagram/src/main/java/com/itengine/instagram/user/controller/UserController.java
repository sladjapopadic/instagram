package com.itengine.instagram.user.controller;

import com.itengine.instagram.user.dto.UserProfileDto;
import com.itengine.instagram.user.dto.UserResponseDto;
import com.itengine.instagram.user.dto.UserUpdateDto;
import com.itengine.instagram.user.service.UserDeleteService;
import com.itengine.instagram.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDeleteService userDeleteService;

    public UserController(UserService userService, UserDeleteService userDeleteService) {
        this.userService = userService;
        this.userDeleteService = userDeleteService;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAccount(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateAccount(userUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete() {
        userDeleteService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserResponseDto> getProfile(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getProfile(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserProfileDto>> getFollowers(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getFollowers(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserProfileDto>> getFollowedUsers(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getFollowedDtoUsers(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<Void> follow(@PathVariable Long userId) {
        userService.follow(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow")
    public ResponseEntity<Void> unfollow(@PathVariable Long userId) {
        userService.unfollow(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/discover")
    public ResponseEntity<List<UserProfileDto>> discover() {
        return new ResponseEntity<>(userService.discover(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserProfileDto>> search(@RequestParam String username) {
        return new ResponseEntity<>(userService.search(username), HttpStatus.OK);
    }

    @PutMapping("/profileImage")
    public ResponseEntity<Void> updateProfileImage(@RequestParam MultipartFile file) throws IOException {
        userService.updateProfileImage(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
