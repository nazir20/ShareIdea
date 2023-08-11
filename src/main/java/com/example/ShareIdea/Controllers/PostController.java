package com.example.ShareIdea.Controllers;

import com.example.ShareIdea.Entity.Post;
import com.example.ShareIdea.Request.PostCreateRequest;
import com.example.ShareIdea.Request.PostUpdateRequest;
import com.example.ShareIdea.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createPost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updateOnPost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest){
        return postService.updateOnePostById(postId, postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnPost(@PathVariable Long postId){
        postService.deleteOnePostById(postId);
    }


}
