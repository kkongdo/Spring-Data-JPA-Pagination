package com.springboot.page.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {
    SUCCESS(true,HttpStatus.OK.value(),"요청 성공"),
    NO_CONTENT(true, HttpStatus.NO_CONTENT.value(),"해당하는 페이지 없음"),
    NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 페이지입니다.");

    private final boolean isSuccess;
    private final Integer code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
