package com.example.ShareIdea.Service;

import com.example.ShareIdea.Entity.Comment;
import com.example.ShareIdea.Entity.Post;
import com.example.ShareIdea.Entity.User;
import com.example.ShareIdea.Repository.CommentRepository;
import com.example.ShareIdea.Request.CommentCreateRequest;
import com.example.ShareIdea.Request.CommentUpdateRequest;
import com.example.ShareIdea.Response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllCommentsByParam(Optional<Long> userId, Optional<Long> postId) {

        List<Comment> comments;
        if(userId.isPresent() && postId.isPresent()){
            comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()){
            comments = commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            comments =  commentRepository.findByPostId(postId.get());
        }else{
            comments =  commentRepository.findAll();
        }

        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }


    public Comment createComment(CommentCreateRequest comment) {

        User foundedUser = userService.getUser(comment.getUserId());
        Post foundedPost = postService.getPostById(comment.getPostId());

        if(foundedUser != null && foundedPost != null){
            Comment newComment = new Comment();
            newComment.setId(comment.getId());
            newComment.setPost(foundedPost);
            newComment.setUser(foundedUser);
            newComment.setText(comment.getText());
            return commentRepository.save(newComment);
        }

        return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {

        Optional<Comment> foundedComment = commentRepository.findById(commentId);

        if(foundedComment.isPresent()){
            Comment updatedComment = foundedComment.get();
            updatedComment.setText(request.getText());
            return commentRepository.save(updatedComment);
        }

        return null;
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
