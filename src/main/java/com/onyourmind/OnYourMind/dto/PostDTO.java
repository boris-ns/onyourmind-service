package com.onyourmind.OnYourMind.dto;

import com.onyourmind.OnYourMind.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostDTO {

    private Long id;
    private String text;
    private Date dateTime;
    private long likes;
    private long dislikes;
    private boolean enabled;
    private Long authorId;
    private String authorName;
    private List<PostCommentDTO> comments;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.dateTime = post.getDateTime();
        this.likes = post.getLikes();
        this.dislikes = post.getDislikes();
        this.enabled = post.isEnabled();
        this.authorId = post.getAuthor().getId();
        this.authorName = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();

        this.comments = post.getComments().stream().map(PostCommentDTO::new).collect(Collectors.toList());
    }
}
