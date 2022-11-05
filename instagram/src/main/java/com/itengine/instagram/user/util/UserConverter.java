package com.itengine.instagram.user.util;

import com.itengine.instagram.user.dto.UserFollowDto;
import com.itengine.instagram.user.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserFollowDto convertToUserFollowerDto(User user) {

        UserFollowDto userFollowDto = new UserFollowDto();

        userFollowDto.setUsername(user.getUsername());
        userFollowDto.setImage(user.getImage());

        return userFollowDto;
    }

    public List<UserFollowDto> convertToUserFollowerDtos(List<User> users) {
        List<UserFollowDto> userFollowDtos = new ArrayList<>();

        for (User user : users) {
            userFollowDtos.add(convertToUserFollowerDto(user));
        }

        return userFollowDtos;
    }
}
