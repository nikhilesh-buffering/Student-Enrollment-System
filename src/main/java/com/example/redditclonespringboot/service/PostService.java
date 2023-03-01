package com.example.redditclonespringboot.service;

import com.example.redditclonespringboot.dto.PostRequest;
import com.example.redditclonespringboot.dto.PostResponse;
import com.example.redditclonespringboot.exception.SpringRedditException;
import com.example.redditclonespringboot.exception.SubRedditNotFoundException;
import com.example.redditclonespringboot.mapper.PostMapper;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.SubReddit;
import com.example.redditclonespringboot.model.User;
import com.example.redditclonespringboot.repository.PostRepository;
import com.example.redditclonespringboot.repository.SubRedditRepository;
import com.example.redditclonespringboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Transactional
    public void save(PostRequest postRequest) {
        SubReddit subReddit = subRedditRepository.findByName(postRequest.getSubRedditName())
                .orElseThrow(()-> new SubRedditNotFoundException("No SubReddit was found with name "+postRequest.getSubRedditName()));
        User user = authService.getCurrentUser();
        Post post = postMapper.mapPostRequestToPost(postRequest,subReddit,user);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new SpringRedditException("Post not found"));
        return postMapper.mapPostToPostResponse(post);
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(Long id) {
        SubReddit subReddit = subRedditRepository.findById(id).orElseThrow(()->
                new SubRedditNotFoundException("SubReddit not found with id "+id));
        List<Post> posts = postRepository.findAllBySubReddit(subReddit);
        return posts.stream().map(postMapper::mapPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(()->
                new UsernameNotFoundException("User with username "+name+" was not found"));
        List<Post> posts = postRepository.findAllByUser(user);
        return posts.stream().map(postMapper::mapPostToPostResponse).collect(Collectors.toList());
    }
}
