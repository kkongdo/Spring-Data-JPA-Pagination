package com.springboot.page.service;

import com.springboot.page.dto.PostResponseDto;
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

    public List<PostResponseDto> findAllPaging(Pageable pageable) {
        Page<Post> postsPages = postRepository.findAll(pageable);
        List<PostResponseDto> postResponseDtoPages = postsPages.map(PostResponseDto::from).getContent();
        return postResponseDtoPages;
    }

    public List<PostResponseDto> findAll() {
        List<Post> postList = postRepository.findAll();
        List<PostResponseDto> responseDtoList = postList.stream().map(PostResponseDto::from).collect(Collectors.toList());
        return responseDtoList;
    }
}
