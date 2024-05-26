package org.example.springbootsveltekitrestback.domain.post.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.standard.base.BaseTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Post extends BaseTime {
    @ManyToOne(fetch = LAZY)
    private Member author;
    private String title;
    @OneToOne(fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    private PostDetail detailBody;
    private boolean published;
}
