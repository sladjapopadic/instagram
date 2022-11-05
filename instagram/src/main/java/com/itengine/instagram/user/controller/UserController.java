package com.itengine.instagram.user.controller;

import com.itengine.instagram.user.dto.UserFollowDto;
import com.itengine.instagram.user.dto.UserResponseDto;
import com.itengine.instagram.user.dto.UserUpdateDto;
import com.itengine.instagram.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAccount(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateAccount(userUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete() {
        userService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserResponseDto> getProfile(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getProfile(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserFollowDto>> getFollowers(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getFollowers(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<UserFollowDto>> getFollowedUsers(@PathVariable Long userId) {
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
}
