package com.example.redditclonespringboot.mapper;

import com.example.redditclonespringboot.dto.SubRedditDto;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {
    @Mapping(target = "noOfPosts",expression = "java(mapPosts(subReddit.getPosts()))")
    SubRedditDto mapSubRedditToDto(SubReddit subReddit);
    default Integer mapPosts(List<Post> posts){
        return posts.size();
    }
    @InheritInverseConfiguration
    @Mapping(target = "posts",ignore = true)
    SubReddit mapDtoToSubReddit(SubRedditDto subRedditDto);
}
