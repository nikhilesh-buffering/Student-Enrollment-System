package com.example.redditclonespringboot.repository;

import com.example.redditclonespringboot.model.Comment;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUser(User user);

    List<Comment> findAllByPost(Post post);
}
