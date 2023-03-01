package com.example.redditclonespringboot.mapper;

import com.example.redditclonespringboot.dto.CommentDto;
import com.example.redditclonespringboot.model.Comment;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "post",source = "post")
    @Mapping(target = "user",source = "user")
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "text",source = "commentDto.text")
    Comment mapCommentDtoToComment(CommentDto commentDto, User user, Post post);

    @Mapping(target = "postId",source = "post.postId")
    @Mapping(target = "username",source = "user.username")
    CommentDto mapCommentToCommentDto(Comment comment);
}
