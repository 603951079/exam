package com.crcc.exam.config.controller;

import com.crcc.exam.domain.dto.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "com.crcc.exam.web")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public BaseResult defaultErrorHandler(HttpServletRequest response, Exception e) {
        BaseResult result = new BaseResult(false, 500, e.getMessage(), null);
        return result;
    }
}
