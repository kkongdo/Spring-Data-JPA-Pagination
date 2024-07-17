package com.springboot.page.dto;

import com.springboot.page.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    @Getter
    @Builder
    public static class PostResponseDto{
        private final Long id;
        private final String title;
        private final String content;
        private final String author;

        public static PostResponseDto from(Post post){
            return PostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor())
                    .build();
        }
    }
}
