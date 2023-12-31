package com.example.ShareIdea.Service;

import com.example.ShareIdea.Entity.Like;
import com.example.ShareIdea.Entity.Post;
import com.example.ShareIdea.Entity.User;
import com.example.ShareIdea.Repository.LikeRepository;
import com.example.ShareIdea.Repository.PostRepository;
import com.example.ShareIdea.Request.PostCreateRequest;
import com.example.ShareIdea.Request.PostUpdateRequest;
import com.example.ShareIdea.Response.LikeResponse;
import com.example.ShareIdea.Response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    public void setLikeService(LikeService likeService){
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {

        List<Post> postList;
        if(userId.isPresent()){
            postList = postRepository.findByUserId(userId);
        }else{
            postList=  postRepository.findAll();
        }
        return postList.stream().map(p-> {
            List<LikeResponse> likes = likeService.getAllLikesByParam(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p, likes);
        }).collect(Collectors.toList());
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }


    public Post createPost(PostCreateRequest newPostRequest) {
        User user = userService.getUser(newPostRequest.getUserId());
        if(user == null) {
            return null;
        }

        Post newPost = new Post();
        newPost.setId(newPostRequest.getId());
        newPost.setText(newPostRequest.getText());
        newPost.setTitle(newPostRequest.getTitle());
        newPost.setUser(user);
        return postRepository.save(newPost);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post updatedPost = post.get();
            updatedPost.setText(postUpdateRequest.getText());
            updatedPost.setTitle(postUpdateRequest.getTitle());
            postRepository.save(updatedPost);
            return updatedPost;
        }

        return null;
    }

    public void deleteOnePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
