package com.example.ShareIdea.Service;

import com.example.ShareIdea.Entity.Like;
import com.example.ShareIdea.Entity.Post;
import com.example.ShareIdea.Entity.User;
import com.example.ShareIdea.Repository.LikeRepository;
import com.example.ShareIdea.Request.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public List<Like> getAllLikesByParam(Optional<Long> userId, Optional<Long> postId) {

        if(userId.isPresent() && postId.isPresent()){
            return likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()){
            return likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            return likeRepository.findByPostId(postId.get());
        }else{
            return likeRepository.findAll();
        }
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
