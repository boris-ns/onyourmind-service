package com.onyourmind.OnYourMind.dto;

import com.onyourmind.OnYourMind.model.PostComment;

import java.util.Date;

public class PostCommentDTO {

    private Long id;
    private String text;
    private Date dateTime;
    private String authorName;
    private Long authorId;
    private Long postId;
    private boolean enabled;

    public PostCommentDTO() {
    }

    public PostCommentDTO(PostComment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.dateTime = comment.getDateTime();
        this.authorName = comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName();
        this.authorId = comment.getAuthor().getId();
        this.postId = comment.getPost().getId();
        this.enabled = comment.isEnabled();
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
