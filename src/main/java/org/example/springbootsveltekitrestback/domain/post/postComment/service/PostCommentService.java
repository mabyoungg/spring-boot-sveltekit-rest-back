package org.example.springbootsveltekitrestback.domain.post.postComment.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.post.postComment.entity.PostComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentService {
    public boolean canDelete(Member actor, PostComment postComment) {
        if (actor == null) return false;
        if (postComment == null) return false;
        if (actor.isAdmin()) return true;
        return actor.equals(postComment.getAuthor());
    }

    public boolean canEdit(Member actor, PostComment postComment) {
        if (actor == null) return false;
        if (postComment == null) return false;
        return actor.equals(postComment.getAuthor());
    }
}