package com.crcc.exam.domain.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResult implements Serializable {
    private boolean success;// 状态说明：true 执行成功； false 发生异常
    private Integer status; // 响应代码200 500
    private String message; // 异常说明
    private Object data; // 业务数据
}
