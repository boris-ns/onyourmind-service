package com.onyourmind.OnYourMind.dto;

import com.onyourmind.OnYourMind.model.Post;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public PostDTO() {
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.dateTime = post.getDateTime();
        this.likes = post.getLikes();
        this.dislikes = post.getDislikes();
        this.enabled = post.isEnabled();
        this.authorId = post.getAuthor().getId();
        this.authorName = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();

        this.comments = post.getComments().stream().map(comment -> new PostCommentDTO(comment)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getDislikes() {
        return dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<PostCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentDTO> comments) {
        this.comments = comments;
    }
}
