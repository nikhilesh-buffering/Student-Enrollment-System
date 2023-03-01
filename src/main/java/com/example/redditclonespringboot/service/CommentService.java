package com.example.redditclonespringboot.service;


import com.example.redditclonespringboot.dto.CommentDto;
import com.example.redditclonespringboot.exception.PostNotFoundException;
import com.example.redditclonespringboot.mapper.CommentMapper;
import com.example.redditclonespringboot.model.NotificationEmail;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.User;
import com.example.redditclonespringboot.repository.CommentRepository;
import com.example.redditclonespringboot.repository.PostRepository;
import com.example.redditclonespringboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;

    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Transactional
    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(()->
                new PostNotFoundException("No post was found with id "+commentDto.getPostId()) );
        commentRepository.save(commentMapper.mapCommentDtoToComment(commentDto,authService.getCurrentUser(),post));
        String message = mailContentBuilder.build(commentDto.getUsername()+" commented on your post "+post.getUrl());
        sendCommentNotification(message,post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getEmail(),"New comment on your post",message));
    }

    public List<CommentDto> getCommentsByUserName(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()->
                new UsernameNotFoundException("User with username "+name+" was not found"));
         return commentRepository.findAllByUser(user).stream()
                 .map(commentMapper::mapCommentToCommentDto)
                 .collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new PostNotFoundException(postId.toString()));
        return commentRepository.findAllByPost(post).stream()
                .map(commentMapper::mapCommentToCommentDto)
                .collect(Collectors.toList());
    }
}
