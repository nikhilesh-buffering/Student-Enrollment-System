package com.example.redditclonespringboot.mapper;

import com.example.redditclonespringboot.dto.PostRequest;
import com.example.redditclonespringboot.dto.PostResponse;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.SubReddit;
import com.example.redditclonespringboot.model.User;
import com.example.redditclonespringboot.repository.CommentRepository;
import com.example.redditclonespringboot.repository.VoteRepository;
import com.example.redditclonespringboot.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "description",source = "postRequest.description")
    @Mapping(target = "subReddit",source = "subReddit")
    @Mapping(target = "user",source = "user")
    @Mapping(target = "voteCount",constant = "0")
    public abstract Post mapPostRequestToPost(PostRequest postRequest, SubReddit subReddit, User user);

    @Mapping(target = "id",source = "postId")
    @Mapping(target = "subRedditName",source = "subReddit.name")
    @Mapping(target = "username",source = "user.username")
    @Mapping(target = "commentCount",expression = "java(commentCount(post))")
    @Mapping(target = "duration",expression = "java(getDuration(post))")
    public abstract PostResponse mapPostToPostResponse(Post post);

    Integer commentCount(Post post){return commentRepository.findAllByPost(post).size();}
    String getDuration(Post post){ return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
