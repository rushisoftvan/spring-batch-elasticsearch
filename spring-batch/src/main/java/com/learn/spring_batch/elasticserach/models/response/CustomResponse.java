package com.learn.spring_batch.elasticserach.models.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class CustomResponse {

    private Object data;

    private int code;

    private String message;



    public static CustomResponse  success(Object data, String message, HttpStatus httpStatus){

        return builder()
                .message(message)
                .code(httpStatus.value())
                .data(data).build();
    }


}
