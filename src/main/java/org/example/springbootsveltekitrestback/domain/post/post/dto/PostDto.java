package org.example.springbootsveltekitrestback.domain.post.post.dto;


import lombok.Getter;
import org.example.springbootsveltekitrestback.domain.post.post.entity.Post;

@Getter
public class PostDto extends AbsPostDto {
    public PostDto(Post post) {
        super(post);
    }
}
