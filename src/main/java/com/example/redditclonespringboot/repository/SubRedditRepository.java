package com.example.redditclonespringboot.repository;

import com.example.redditclonespringboot.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit,Long> {
    Optional<SubReddit> findByName(String name);
    Optional<SubReddit> findById(Long id);
}
