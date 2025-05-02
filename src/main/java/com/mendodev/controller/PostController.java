package com.mendodev.controller;
import com.mendodev.domain.post.*;
import com.mendodev.infra.bugs.BugsManagment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private IPostRepository postRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registerPost(@RequestBody @Valid RegisterDataPost registerDataPost) {
        Post post = postRepository.save(new Post(registerDataPost));
        return ResponseEntity.ok(
                new AnswerDataPost(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCategory(),
                        post.getTags(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()));
    }

    // Get all posts
    @GetMapping
    public ResponseEntity <List<AnswerDataPost>> allPosts ( @RequestParam(required = false) String title,
                                                            @RequestParam(required = false) String content,
                                                            @RequestParam(required = false) String category,
                                                            @RequestParam(required = false) List<String> tags) {
        List<Post> posts;
        if (title != null) {
            posts = postRepository.findByTitleLikeIgnoreCase("%" + title + "%");
            if (posts.isEmpty()) {
                throw new BugsManagment.CustomNotFoundException("No posts matched your title");
            }
        } else if (category != null) {
            posts = postRepository.findByCategoryLikeIgnoreCase("%" + category + "%");
            if (posts.isEmpty()) {
                throw new BugsManagment.CustomNotFoundException("No posts matched your category");
            }
        } else if (content != null) {
            posts = postRepository.findByContentLikeIgnoreCase("%" + content + "%");
            if (posts.isEmpty()) {
                throw new BugsManagment.CustomNotFoundException("No posts matched your content");
            }
        } else if (tags != null) {
            posts = postRepository.findByTagsContains(tags);
            if (posts.isEmpty()) {
                throw new BugsManagment.CustomNotFoundException("No posts matched your tags");
            }
        } else {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
            if (posts.isEmpty()) {
                throw new BugsManagment.CustomNotFoundException("No posts found");
            }
        }

        List<AnswerDataPost> dtoPosts = posts.stream()
                .map(post -> new AnswerDataPost(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCategory(),
                        post.getTags(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(dtoPosts);
    }

    // Get a single post by id
    @GetMapping("/{id}")
    public ResponseEntity <AnswerDataPost> singlePost (@PathVariable Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return ResponseEntity.ok(new AnswerDataPost(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getTags(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        ));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity updatePost(@RequestBody @Valid UpdateDataPost updateDataPost, @PathVariable Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        post.updatePost(updateDataPost);
        postRepository.save(post);

        return ResponseEntity.ok(new AnswerDataPost(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getTags(),
                post.getCreatedAt(),
                post.getUpdatedAt())
        );
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
