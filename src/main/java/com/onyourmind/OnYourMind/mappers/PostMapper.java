package com.onyourmind.OnYourMind.mappers;

import com.onyourmind.OnYourMind.dto.PostDTO;
import com.onyourmind.OnYourMind.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {

    private PostMapper() {
    }

    public static PostDTO toDto(Post post) {
        return new PostDTO(post);
    }

    public static List<PostDTO> toListDto(List<Post> posts) {
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }
}
