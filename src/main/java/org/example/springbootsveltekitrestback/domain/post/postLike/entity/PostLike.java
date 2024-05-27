package org.example.springbootsveltekitrestback.domain.post.postLike.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostLike {
    @EmbeddedId
    @Delegate
    @EqualsAndHashCode.Include
    private PostLikeId id;

    @Builder
    private static PostLike of(Post post, Member member) {
        return new PostLike(
                PostLikeId.builder()
                        .post(post)
                        .member(member)
                        .build()
        );
    }
}
