package com.itengine.instagram.util.util;

import com.itengine.instagram.user.dto.UserProfileDto;
import com.itengine.instagram.user.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public List<UserProfileDto> convertToUserProfileDtos(List<User> users) {
        List<UserProfileDto> userProfileDtos = new ArrayList<>();

        for (User user : users) {
            userProfileDtos.add(convertToUserProfileDto(user));
        }

        return userProfileDtos;
    }

    private UserProfileDto convertToUserProfileDto(User user) {
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setUserId(user.getId());

        return userProfileDto;
    }
}
