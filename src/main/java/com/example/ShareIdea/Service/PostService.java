package com.example.ShareIdea.Service;

import com.example.ShareIdea.Entity.Post;
import com.example.ShareIdea.Entity.User;
import com.example.ShareIdea.Repository.PostRepository;
import com.example.ShareIdea.Request.PostCreateRequest;
import com.example.ShareIdea.Request.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if(userId.isPresent()){
            return postRepository.findByUserId(userId);
        }else{
            return postRepository.findAll();
        }
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
