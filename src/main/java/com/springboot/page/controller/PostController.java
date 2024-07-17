package com.springboot.page.controller;

import com.springboot.page.common.response.BaseResponse;
import com.springboot.page.common.response.BaseResponseStatus;
import com.springboot.page.dto.PostDto;
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
    public BaseResponse<List<PostDto.PostResponseDto>> getPost(@RequestParam(required = false) Integer page) {
        List<PostDto.PostResponseDto> postResponseDtoList;
        if (page == null) {
            postResponseDtoList = postService.findAll(); // 페이지가 없는 전체 목록을 출력할 때
        } else {
            // 페이징을 원하는 목록 출력
            int size = 3; // 페이지 하나에 가져올 객체의 수 : 여기서는 3개
            Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.ASC, "title");
            // 첫번째 인자는 시작 페이지 : 1부터 시작할려면 -1을 해줌,
            // 두번째 인자는 size, 세번째 인자는 정렬(오름차순or내림차순),
            // 네번째 인자는 정렬 기준
            postResponseDtoList = postService.findAllPaging(pageable);
        }
//        return postResponseDtoList != null && !postResponseDtoList.isEmpty() ?
//                ResponseEntity.status(HttpStatus.OK).body(postResponseDtoList) :
//                ResponseEntity.status((HttpStatus.NOT_FOUND)).build();

        // CustomResponse를 이용하여 응답 전달
        return postResponseDtoList != null && !postResponseDtoList.isEmpty() ?
                new BaseResponse<>(postResponseDtoList) : new BaseResponse<>();
    }
}
