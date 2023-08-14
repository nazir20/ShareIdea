package com.example.ShareIdea.Service;

import com.example.ShareIdea.Entity.Like;
import com.example.ShareIdea.Entity.Post;
import com.example.ShareIdea.Entity.User;
import com.example.ShareIdea.Repository.LikeRepository;
import com.example.ShareIdea.Request.LikeCreateRequest;
import com.example.ShareIdea.Response.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }



    public List<LikeResponse> getAllLikesByParam(Optional<Long> userId, Optional<Long> postId) {

        List<Like> likes;

        if(userId.isPresent() && postId.isPresent()){
            likes = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()){
            likes =  likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            likes =  likeRepository.findByPostId(postId.get());
        }else{
            likes = likeRepository.findAll();
        }

        return likes.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createLike(LikeCreateRequest request) {

        User foundedUser = userService.getUser(request.getUserId());
        Post foundedPost = postService.getPostById(request.getPostId());

        if(foundedUser != null && foundedPost != null){
            Like newLike = new Like();
            newLike.setId(request.getId());
            newLike.setUser(foundedUser);
            newLike.setPost(foundedPost);
            return likeRepository.save(newLike);
        }

        return null;
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
