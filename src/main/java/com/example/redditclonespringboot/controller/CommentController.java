package com.example.redditclonespringboot.controller;

import com.example.redditclonespringboot.dto.CommentDto;
import com.example.redditclonespringboot.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentDto commentDto){
        commentService.save(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/by-username/{name}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUserName(name));
    }

    @GetMapping("/by-postid/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
    }
}
