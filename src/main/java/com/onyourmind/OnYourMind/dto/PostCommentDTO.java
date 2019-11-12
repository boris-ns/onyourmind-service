package com.onyourmind.OnYourMind.dto;

import com.onyourmind.OnYourMind.model.PostComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class PostCommentDTO {

    private Long id;
    private String text;
    private Date dateTime;
    private String authorName;
    private Long authorId;
    private Long postId;
    private boolean enabled;

    public PostCommentDTO(PostComment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.dateTime = comment.getDateTime();
        this.authorName = comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName();
        this.authorId = comment.getAuthor().getId();
        this.postId = comment.getPost().getId();
        this.enabled = comment.isEnabled();
    }
}
