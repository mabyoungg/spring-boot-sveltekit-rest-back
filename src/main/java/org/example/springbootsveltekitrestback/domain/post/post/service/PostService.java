package org.example.springbootsveltekitrestback.domain.post.post.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.post.entity.PostDetail;
import org.example.springbootsveltekitrestback.domain.post.post.repository.PostDetailRepository;
import org.example.springbootsveltekitrestback.domain.post.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final PostDetailRepository postDetailRepository;

    @Transactional
    public Post write(Member author, String title, String body, boolean published) {
        Post post = Post.builder()
                .author(author)
                .title(title)
                .published(published)
                .build();

        postRepository.save(post);

        post.setDetailBody(
                PostDetail
                        .builder()
                        .post(post)
                        .name("common__body")
                        .build()
        );

        saveBody(post, body);

        return post;
    }

    private void saveBody(Post post, String body) {
        PostDetail detailBody = post.getDetailBody();

        if (detailBody == null) {
            detailBody = findDetailOrMake(post, "common__body");
            post.setDetailBody(detailBody);
        }

        detailBody.setVal(body);
    }

    private PostDetail findDetailOrMake(Post post, String name) {
        Optional<PostDetail> opDetailBody = postDetailRepository.findByPostAndName(post, name);

        PostDetail detailBody = opDetailBody.orElseGet(() -> postDetailRepository.save(
                PostDetail.builder()
                        .post(post)
                        .name(name)
                        .build()
        ));

        return detailBody;
    }

    public List<Post> findByPublished(boolean published) {
        return postRepository.findByPublishedOrderByIdDesc(published);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Transactional
    public void edit(Post post, String title, String body, boolean published) {
        post.setTitle(title);
        post.setPublished(published);

        saveBody(post, body);
    }

    public boolean canRead(Member actor, Post post) {
        if (actor == null) return false;
        if (post == null) return false;

        if (actor.isAdmin()) return true;
        if (post.isPublished()) return true;

        return actor.equals(post.getAuthor());
    }

    public boolean canEdit(Member actor, Post post) {
        if (actor == null) return false;
        if (post == null) return false;

        return actor.equals(post.getAuthor());
    }

    public Boolean canDelete(Member actor, Post post) {
        if (actor == null) return false;
        if (post == null) return false;

        if (actor.isAdmin()) return true;

        return actor.equals(post.getAuthor());
    }

    public Boolean canLike(Member actor, Post post) {
        if (actor == null) return false;
        if (post == null) return false;

        return !post.hasLike(actor);
    }

    public Boolean canCancelLike(Member actor, Post post) {
        if (actor == null) return false;
        if (post == null) return false;

        return post.hasLike(actor);
    }

    @Transactional
    public void like(Member actor, Post post) {
        post.addLike(actor);
    }

    @Transactional
    public void cancelLike(Member actor, Post post) {
        post.deleteLike(actor);
    }
}
