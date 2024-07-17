package com.springboot.page.exceptions;

import com.springboot.page.common.response.BaseResponse;
import com.springboot.page.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public BaseResponse<BaseResponseStatus> notFoundExceptionException(NotFoundException exception) {
        return new BaseResponse<>(exception.getStatus());
    }
}
