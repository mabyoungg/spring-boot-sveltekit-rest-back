package org.example.springbootsveltekitrestback.domain.post.post.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.post.post.dto.PostDto;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.post.service.PostService;
import org.example.springbootsveltekitrestback.global.exceptions.GlobalException;
import org.example.springbootsveltekitrestback.global.rsData.RsData;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    public record GetPostsResponseBody(@NonNull List<PostDto> items) {
    }

    @GetMapping("")
    public RsData<GetPostsResponseBody> getPosts() {
        List<Post> items = postService.findByPublished(true);

        return RsData.of(
                new GetPostsResponseBody(
                        items.stream()
                                .map(PostDto::new)
                                .toList()
                )
        );
    }

    public record GetPostResponseBody(@NonNull PostDto item) {
    }

    @GetMapping("/{id}")
    public RsData<GetPostResponseBody> getPost(
            @PathVariable("id") long id
    ) {
        Post post = postService.findById(id).orElseThrow(GlobalException.E404::new);

        return RsData.of(
                new GetPostResponseBody(new PostDto(post))
        );
    }
}
