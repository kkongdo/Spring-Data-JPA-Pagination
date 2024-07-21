package com.springboot.page.service;

import com.springboot.page.dto.PostDto;
import com.springboot.page.entity.Post;
import com.springboot.page.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDto.PostResponseDto> findAll(Pageable pageable) {
        // 페이징을 원하는 목록 출력, 첫번째 인자는 시작 페이지 : 1부터 시작할려면 -1을 해줌, 두번째 인자는 size, 세번째 인자는 정렬(오름차순or내림차순), 네번째 인자는 정렬 기준
        int size = 3; // 페이지 하나에 가져올 객체의 수 : 여기서는 3개
        Pageable page = PageRequest.of(pageable.getPageNumber() - 1, size, Sort.Direction.ASC, "title");
        Page<Post> postsPageList = postRepository.findAll(page);
        return postsPageList.map(PostDto.PostResponseDto::from).getContent();
    }
}
