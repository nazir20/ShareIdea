package com.example.ShareIdea.Response;

import com.example.ShareIdea.Entity.Like;
import com.example.ShareIdea.Entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    private Long postId;
    private Long userId;
    private String username;
    private String postTitle;
    private String postText;
    private List<LikeResponse> postLikes;

    public PostResponse(Post entity, List<LikeResponse> likes){

        this.postId = entity.getId();
        this.userId = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.postTitle = entity.getTitle();
        this.postText = entity.getText();
        this.postLikes = likes;

    }
}
