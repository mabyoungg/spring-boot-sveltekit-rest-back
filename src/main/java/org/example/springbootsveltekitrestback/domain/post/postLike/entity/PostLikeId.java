package org.example.springbootsveltekitrestback.domain.post.postLike.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;

import java.io.Serializable;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
public class PostLikeId implements Serializable {
    @ManyToOne(fetch = LAZY)
    private Post post;
    @ManyToOne(fetch = LAZY)
    private Member member;
}
