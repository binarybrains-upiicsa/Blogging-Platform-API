package com.mendodev.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleLikeIgnoreCase(String title);
    Optional<Post> findById(Long id);
    List<Post> findByContentLikeIgnoreCase(String content);
    List<Post> findByCategoryLikeIgnoreCase(String category);
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByTagsContains(List<String> tags);
}
