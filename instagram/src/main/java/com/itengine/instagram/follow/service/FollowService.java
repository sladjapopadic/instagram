package com.itengine.instagram.follow.service;

import com.itengine.instagram.follow.model.Follow;
import com.itengine.instagram.follow.repository.FollowRepository;
import com.itengine.instagram.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void createFollow(User followFrom, User followTo) {

        if (!followRepository.existsByFollowFromAndFollowTo(followFrom, followTo)) {
            return;
        }

        Follow follow = new Follow(followFrom, followTo);
        followRepository.save(follow);
    }

    public void removeFollow(User followFrom, User followTo) {
        followRepository.deleteByFollowFromAndFollowTo(followFrom, followTo);
    }

    public List<Follow> getSuggestions(Long userId) {
        return followRepository.findSuggestions(userId);
    }
}
