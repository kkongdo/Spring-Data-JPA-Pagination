package com.springboot.page.dto;

import com.springboot.page.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    public static PostResponseDto from(Post post){
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor()
        );
    }
}
