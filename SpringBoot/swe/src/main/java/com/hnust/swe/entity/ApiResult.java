package com.hnust.swe.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {

    private int code;//状态码

    private String message;//错误信息
  
    private T data;//返回值

    
}
