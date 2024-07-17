package com.springboot.page.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.springboot.page.common.response.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess","code","message","data"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    @JsonIgnore
    private final Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
    // 요청 성공
    public BaseResponse(T data){
        this.isSuccess = SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.data = data;
    }
    // 요청 성공
    public BaseResponse(){
        this.isSuccess = BaseResponseStatus.NO_CONTENT.isSuccess();
        this.code = BaseResponseStatus.NO_CONTENT.getCode();
        this.message = BaseResponseStatus.NO_CONTENT.getMessage();
    }

    //요청 실패
    public BaseResponse(BaseResponseStatus baseResponseStatus){
        this.isSuccess = baseResponseStatus.isSuccess();
        this.code = baseResponseStatus.getCode();
        this.message = baseResponseStatus.getMessage();
    }
    //요청 실패
    public BaseResponse(BaseResponseStatus baseResponseStatus, String message){
        this.isSuccess = baseResponseStatus.isSuccess();
        this.code = baseResponseStatus.getCode();
        this.message = message;
    }
}
