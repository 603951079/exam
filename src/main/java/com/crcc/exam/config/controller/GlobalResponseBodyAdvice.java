package com.crcc.exam.config.controller;

import com.crcc.exam.domain.dto.BaseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.crcc.exam.web")
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        BaseResult baseResult = new BaseResult(true, 200, null, o);
        if(o instanceof String) {
            try {
                log.debug(" o instanceof String is true");
                return new ObjectMapper().writeValueAsString(baseResult);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }
}
