package com.mendodev.domain.post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private String category;

    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tags")
    private List<String> tags = new ArrayList<>();

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Post(RegisterDataPost registerDataPost) {
        this.title = registerDataPost.title();
        this.content = registerDataPost.content();
        this.category = registerDataPost.category();
        this.tags = registerDataPost.tags();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePost(UpdateDataPost updateDataPost) {
        if (updateDataPost.title() != null) {
            this.title = updateDataPost.title();
        }

        if (updateDataPost.content() != null) {
            this.content = updateDataPost.content();
        }

        if (updateDataPost.category() != null) {
            this.category = updateDataPost.category();
        }

        if (updateDataPost.tags() != null) {
            this.tags = updateDataPost.tags();
        }

        this.updatedAt = LocalDateTime.now();
    }
}
