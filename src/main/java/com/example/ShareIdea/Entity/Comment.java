package com.example.ShareIdea.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    private Long id;

    private Long postId;
    private Long userId;

    @Lob
    @Column(columnDefinition = "text")
    private String text;
}
