package org.example.springbootsveltekitrestback.domain.post.postComment.repository;

import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.postComment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Optional<PostComment> findTop1ByPostAndAuthorAndPublishedAndBodyOrderByIdDesc(Post post, Member author, boolean published, String body);
}
