package org.example.springbootsveltekitrestback.domain.post.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.postComment.entity.PostComment;
import org.example.springbootsveltekitrestback.domain.post.postLike.entity.PostLike;
import org.example.springbootsveltekitrestback.standard.base.BaseTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
public class Post extends BaseTime {
    @OneToMany(mappedBy = "id.post", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<PostLike> likes = new ArrayList<>();
    @Column(columnDefinition = "BIGINT default 0")
    @Setter(PROTECTED)
    private long likesCount;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    @OrderBy("id DESC")
    private List<PostComment> comments = new ArrayList<>();
    @Column(columnDefinition = "BIGINT default 0")
    @Setter(PROTECTED)
    private long commentsCount;

    @ManyToOne(fetch = LAZY)
    private Member author;
    private String title;
    @OneToOne(fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    private PostDetail detailBody;
    @Column(columnDefinition = "BOOLEAN default false")
    private boolean published;
    @Column(columnDefinition = "BOOLEAN default false")
    private boolean listed;

    public void increaseLikesCount() {
        likesCount++;
    }

    private void decreaseLikesCount() {
        likesCount--;
    }

    public void addLike(Member member) {
        likes.add(PostLike.builder()
                .post(this)
                .member(member)
                .build());

        increaseLikesCount();
    }

    public void deleteLike(Member member) {
        likes.remove(
                PostLike.builder()
                        .post(this)
                        .member(member)
                        .build()
        );

        decreaseLikesCount();
    }

    public boolean hasLike(Member actor) {
        return likes.contains(
                PostLike.builder()
                        .post(this)
                        .member(actor)
                        .build()
        );
    }

    public void increaseCommentsCount() {
        commentsCount++;
    }

    private void decreaseCommentsCount() {
        commentsCount--;
    }

    public PostComment addComment(Member author, String body) {
        return addComment(author, body, true);
    }

    public PostComment addComment(Member author, String body, boolean published) {
        PostComment postComment = PostComment.builder()
                .post(this)
                .author(author)
                .body(body)
                .published(published)
                .build();

        comments.add(postComment);

        increaseCommentsCount();

        return postComment;
    }

    public void deleteComment(PostComment postComment) {
        comments.remove(postComment);

        decreaseCommentsCount();
    }

    public Optional<PostComment> findCommentById(long postCommentId) {
        return comments.stream()
                .filter(it -> it.getId().equals(postCommentId))
                .findFirst();
    }

    public void setModified() {
        setModifyDate(LocalDateTime.now());
    }
}
