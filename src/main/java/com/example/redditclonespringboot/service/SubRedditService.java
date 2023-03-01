package com.example.redditclonespringboot.service;

import com.example.redditclonespringboot.dto.SubRedditDto;
import com.example.redditclonespringboot.exception.SpringRedditException;
import com.example.redditclonespringboot.mapper.SubRedditMapper;
import com.example.redditclonespringboot.model.SubReddit;
import com.example.redditclonespringboot.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubRedditService {
    private final SubRedditMapper subRedditMapper;
    private final SubRedditRepository subRedditRepository;

    @Transactional
    public SubRedditDto save(SubRedditDto subRedditDto){
        SubReddit save = subRedditRepository.save(subRedditMapper.mapDtoToSubReddit(subRedditDto));
        subRedditDto.setId(save.getId());
        return subRedditDto;
    }

    @Transactional(readOnly = true)
    public List<SubRedditDto> getAll() {
        return subRedditRepository.findAll().stream().map(subRedditMapper::mapSubRedditToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubRedditDto getSubRedditById(Long id) {
        SubReddit subReddit = subRedditRepository.findById(id)
                .orElseThrow(()->new SpringRedditException("No SubReddit Found."));
        return subRedditMapper.mapSubRedditToDto(subReddit);
    }
}
