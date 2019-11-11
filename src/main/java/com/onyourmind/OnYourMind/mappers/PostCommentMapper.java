package com.onyourmind.OnYourMind.mappers;

import com.onyourmind.OnYourMind.dto.PostCommentDTO;
import com.onyourmind.OnYourMind.model.PostComment;

import java.util.List;
import java.util.stream.Collectors;

public class PostCommentMapper {

    private PostCommentMapper() {
    }

    public static PostCommentDTO toDto(PostComment comment) {
        return new PostCommentDTO(comment);
    }

    public static List<PostCommentDTO> toListDto(List<PostComment> comments) {
        return comments.stream().map(PostCommentDTO::new).collect(Collectors.toList());
    }
}
