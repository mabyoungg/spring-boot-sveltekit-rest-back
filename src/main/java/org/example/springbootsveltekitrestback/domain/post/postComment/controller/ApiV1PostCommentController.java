package org.example.springbootsveltekitrestback.domain.post.postComment.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.post.service.PostService;
import org.example.springbootsveltekitrestback.domain.post.postComment.dto.PostCommentDto;
import org.example.springbootsveltekitrestback.domain.post.postComment.entity.PostComment;
import org.example.springbootsveltekitrestback.domain.post.postComment.service.PostCommentService;
import org.example.springbootsveltekitrestback.global.exceptions.GlobalException;
import org.example.springbootsveltekitrestback.global.rq.Rq;
import org.example.springbootsveltekitrestback.global.rsData.RsData;
import org.example.springbootsveltekitrestback.standard.base.Empty;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/postComments")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PostCommentController {
    private final Rq rq;
    private final PostService postService;
    private final PostCommentService postCommentService;
    @PersistenceContext
    private EntityManager entityManager;


    public record GetPostCommentsResponseBody(
            @NonNull List<PostCommentDto> items
    ) {
    }

    @GetMapping("/{postId}")
    public RsData<GetPostCommentsResponseBody> getPosts(
            @PathVariable long postId
    ) {
        List<PostComment> items = postService.findById(postId)
                .orElseThrow(GlobalException.E404::new)
                .getComments();

        List<PostCommentDto> _items = items.stream()
                .map(this::postCommentToDto)
                .collect(Collectors.toList());

        return RsData.of(
                new GetPostCommentsResponseBody(
                        _items
                )
        );
    }

    @DeleteMapping("/{postId}/{postCommentId}")
    @Transactional
    public RsData<Empty> delete(
            @PathVariable long postId,
            @PathVariable long postCommentId
    ) {
        Post post = postService.findById(postId).orElseThrow(GlobalException.E404::new);

        PostComment postComment = post.findCommentById(postCommentId)
                .orElseThrow(GlobalException.E404::new);

        if (!postCommentService.canDelete(rq.getMember(), postComment))
            throw new GlobalException("403-1", "권한이 없습니다.");

        postService.deleteComment(post, postComment);

        return RsData.of(
                "댓글이 삭제되었습니다."
        );
    }

    public record WriteCommentRequestBody(@NotBlank String body) {
    }

    public record WriteCommentResponseBody(@NonNull PostCommentDto item) {
    }

    @PostMapping("/{postId}")
    @Transactional
    public RsData<WriteCommentResponseBody> write(
            @PathVariable long postId,
            @Valid @RequestBody WriteCommentRequestBody body
    ) {
        Post post = postService.findById(postId).orElseThrow(GlobalException.E404::new);

        PostComment postComment = postService.writeComment(rq.getMember(), post, body.body);

        // postComment.getId() null, flush() DB 반영
        entityManager.flush();

        return RsData.of(
                "댓글이 작성되었습니다.",
                new WriteCommentResponseBody(postCommentToDto(postComment))
        );
    }

    public record EditCommentRequestBody(@NotBlank String body) {
    }

    public record EditCommentResponseBody(@NonNull PostCommentDto item) {
    }

    @PutMapping("/{postId}/{postCommentId}")
    @Transactional
    public RsData<EditCommentResponseBody> edit(
            @PathVariable long postId,
            @PathVariable long postCommentId,
            @Valid @RequestBody EditCommentRequestBody body
    ) {
        Post post = postService.findById(postId).orElseThrow(GlobalException.E404::new);

        PostComment postComment = post.findCommentById(postCommentId)
                .orElseThrow(GlobalException.E404::new);

        postService.editComment(post, postComment, body.body);

        return RsData.of(
                "댓글이 수정되었습니다.",
                new EditCommentResponseBody(postCommentToDto(postComment))
        );
    }


    private PostCommentDto postCommentToDto(PostComment postComment) {
        PostCommentDto dto = new PostCommentDto(postComment);
        dto.setActorCanDelete(postCommentService.canDelete(rq.getMember(), postComment));
        dto.setActorCanEdit(postCommentService.canEdit(rq.getMember(), postComment));

        return dto;
    }
}
