package org.example.springbootsveltekitrestback.domain.post.postComment.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.post.post.service.PostService;
import org.example.springbootsveltekitrestback.domain.post.postComment.dto.PostCommentDto;
import org.example.springbootsveltekitrestback.domain.post.postComment.entity.PostComment;
import org.example.springbootsveltekitrestback.domain.post.postComment.service.PostCommentService;
import org.example.springbootsveltekitrestback.global.exceptions.GlobalException;
import org.example.springbootsveltekitrestback.global.rq.Rq;
import org.example.springbootsveltekitrestback.global.rsData.RsData;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    private PostCommentDto postCommentToDto(PostComment postComment) {
        PostCommentDto dto = new PostCommentDto(postComment);
        dto.setActorCanDelete(postCommentService.canDelete(rq.getMember(), postComment));
        dto.setActorCanEdit(postCommentService.canEdit(rq.getMember(), postComment));

        return dto;
    }
}
