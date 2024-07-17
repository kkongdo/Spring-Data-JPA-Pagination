package com.springboot.page.exceptions;

import com.springboot.page.common.response.BaseResponseStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class NotFoundException extends BaseException{
    public NotFoundException(BaseResponseStatus status) {
        super(status);
    }
}
