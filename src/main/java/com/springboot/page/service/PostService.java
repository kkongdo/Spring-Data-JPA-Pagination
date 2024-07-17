package com.springboot.page.service;

import com.springboot.page.dto.PostDto;
import com.springboot.page.entity.Post;
import com.springboot.page.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDto.PostResponseDto> findAllPaging(Pageable pageable) {
        Page<Post> postsPages = postRepository.findAll(pageable);
        List<PostDto.PostResponseDto> postResponseDtoPages = postsPages.map(PostDto.PostResponseDto::from).getContent();
        return postResponseDtoPages;
    }

    public List<PostDto.PostResponseDto> findAll() {
        List<Post> postList = postRepository.findAll();
        List<PostDto.PostResponseDto> responseDtoList = postList.stream().map(PostDto.PostResponseDto::from).collect(Collectors.toList());
        return responseDtoList;
    }
}
