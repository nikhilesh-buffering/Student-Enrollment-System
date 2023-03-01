package com.example.redditclonespringboot.repository;

import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.User;
import com.example.redditclonespringboot.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
