package com.example.redditclonespringboot.repository;

import com.example.redditclonespringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
       Optional<User> findByUsername(String username);
}
