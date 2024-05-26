package org.example.springbootsveltekitrestback.domain.post.post.repository;

import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.post.entity.PostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostDetailRepository extends JpaRepository<PostDetail, Long> {
    Optional<PostDetail> findByPostAndName(Post post, String name);
}
