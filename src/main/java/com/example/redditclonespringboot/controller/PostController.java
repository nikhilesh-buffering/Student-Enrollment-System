package com.example.redditclonespringboot.controller;


import com.example.redditclonespringboot.dto.PostRequest;
import com.example.redditclonespringboot.dto.PostResponse;
import com.example.redditclonespringboot.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubReddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubReddit(id));
    }

    @GetMapping("/by-username/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
}
