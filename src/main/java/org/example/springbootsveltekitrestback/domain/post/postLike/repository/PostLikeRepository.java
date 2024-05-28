package org.example.springbootsveltekitrestback.domain.post.postLike.repository;

import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.postLike.entity.PostLike;
import org.example.springbootsveltekitrestback.domain.post.postLike.entity.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    List<PostLike> findByIdPostInAndIdMember(List<Post> posts, Member member);
}
