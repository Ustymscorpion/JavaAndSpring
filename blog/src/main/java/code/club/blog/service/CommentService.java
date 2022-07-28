package code.club.blog.service;

import code.club.blog.model.Comment;

public interface CommentService {
    Comment save(Comment comment);
    void delete(Comment comment);
}
