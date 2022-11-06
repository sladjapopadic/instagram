package com.itengine.instagram.post.util;

import com.itengine.instagram.comment.util.CommentConverter;
import com.itengine.instagram.post.dto.PostDto;
import com.itengine.instagram.post.model.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class PostConverter {

    private final CommentConverter commentConverter;

    public PostConverter(CommentConverter commentConverter) {
        this.commentConverter = commentConverter;
    }

    private PostDto convertToPostDto(Post post) {

        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setUsername(post.getUser().getUsername());
        postDto.setProfileImage(post.getUser().getImage());
        postDto.setImage(post.getImage());
        postDto.setCaption(post.getCaption());
        postDto.setComments(commentConverter.convertToCommentDtos(post.getComments()));
        postDto.setLikes(post.getLikes().size());

        return postDto;
    }

    public List<PostDto> convertToSortedPostDtos(List<Post> posts) {
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        List<PostDto> postDtos = new ArrayList<>();

        for(Post post : posts) {
            postDtos.add(convertToPostDto(post));
        }

        return postDtos;
    }
}
