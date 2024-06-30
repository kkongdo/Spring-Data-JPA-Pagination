package com.springboot.page.controller;

import com.springboot.page.dto.PostResponseDto;
import com.springboot.page.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPost(@RequestParam(required = false) Integer page) {
        List<PostResponseDto> postResponseDtoList;
        if (page == null) {
            postResponseDtoList = postService.findAll();
        } else {
            int size = 3;
            Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "title");
            postResponseDtoList = postService.findAllPaging(pageable);
        }
        return postResponseDtoList != null && !postResponseDtoList.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(postResponseDtoList) :
                ResponseEntity.status((HttpStatus.NOT_FOUND)).build();
    }
}
