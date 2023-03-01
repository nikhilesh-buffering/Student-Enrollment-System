package com.example.redditclonespringboot.service;

import com.example.redditclonespringboot.dto.VoteDto;
import com.example.redditclonespringboot.exception.PostNotFoundException;
import com.example.redditclonespringboot.exception.SpringRedditException;
import com.example.redditclonespringboot.model.Post;
import com.example.redditclonespringboot.model.Vote;
import com.example.redditclonespringboot.model.VoteType;
import com.example.redditclonespringboot.repository.PostRepository;
import com.example.redditclonespringboot.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()->
                new PostNotFoundException("No post found with id "+voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,authService.getCurrentUser());
        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already "+voteDto.getVoteType()+"'ed this post");
        }
        if(VoteType.UPVOTE.equals(voteDto.getVoteType())) post.setVoteCount(post.getVoteCount()+1);
        if(VoteType.DOWNVOTE.equals(voteDto.getVoteType())) post.setVoteCount(post.getVoteCount()-1);
        voteRepository.save(mapVoteDtoToVote(voteDto,post));
        postRepository.save(post);
    }

    private Vote mapVoteDtoToVote(VoteDto voteDto,Post post) {
        return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
    }
}
