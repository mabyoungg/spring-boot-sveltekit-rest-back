package org.example.springbootsveltekitrestback.domain.post.post.dto;

import lombok.Getter;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;
import org.springframework.lang.NonNull;

@Getter
public class PostWithBodyDto extends PostDto {
    @NonNull
    private String body;

    public PostWithBodyDto(Post post) {
        super(post);
        this.body = post.getDetailBody().getVal();
    }
}
