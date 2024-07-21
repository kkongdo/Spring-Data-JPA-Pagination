package com.springboot.page.controller;

import com.springboot.page.common.response.BaseResponse;
import com.springboot.page.dto.PostDto;
import com.springboot.page.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public BaseResponse<List<PostDto.PostResponseDto>> getPost(Pageable pageable) {
        List<PostDto.PostResponseDto> postResponseDtoList = postService.findAll(pageable);
        // CustomResponse를 이용하여 응답 전달
        return new BaseResponse<>(postResponseDtoList);
    }
}
