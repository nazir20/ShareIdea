package com.example.ShareIdea.Response;

import com.example.ShareIdea.Entity.Post;
import lombok.Data;

@Data
public class PostResponse {

    private Long postId;
    private Long userId;
    private String username;
    private String postTitle;
    private String postText;

    public PostResponse(Post entity){

        this.postId = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.postTitle = entity.getTitle();
        this.postText = entity.getText();

    }
}
