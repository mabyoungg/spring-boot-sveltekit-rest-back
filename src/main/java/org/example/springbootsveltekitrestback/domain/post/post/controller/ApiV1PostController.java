package org.example.springbootsveltekitrestback.domain.post.post.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.post.post.dto.PostDto;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.example.springbootsveltekitrestback.domain.post.post.service.PostService;
import org.example.springbootsveltekitrestback.global.rsData.RsData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    @Getter
    public static class GetPostsResponseBody {
        private List<PostDto> items;

        public GetPostsResponseBody(List<Post> items) {
            this.items = items.stream()
                    .map(PostDto::new)
                    .toList();
        }
    }

    @GetMapping("")
    public RsData<GetPostsResponseBody> getPosts() {
        List<Post> items = postService.findByPublished(true);

        return RsData.of(
                "200-1",
                "성공",
                new GetPostsResponseBody(items)
        );
    }
}
