package org.example.springbootsveltekitrestback.domain.post.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootsveltekitrestback.standard.base.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "name"})
})
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
@ToString(callSuper = true)
public class PostDetail extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Post post;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String val;
}
