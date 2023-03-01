package com.example.redditclonespringboot.repository;

import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.SubReddit;
import com.example.redditclonespringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(Long id);

    List<Post> findAllBySubReddit(SubReddit subReddit);

    List<Post> findAllByUser(User user);
}
