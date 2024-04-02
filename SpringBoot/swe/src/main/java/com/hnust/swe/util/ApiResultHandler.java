package com.hnust.swe.util;

import com.hnust.swe.entity.ApiResult;

public class ApiResultHandler {

    /*public static ApiResult success(Object object) {
        ApiResult apiResult = new ApiResult();
        apiResult.setData(object);
        apiResult.setCode(200);
        apiResult.setMessage("请求成功");
        return apiResult;
    }

    public static ApiResult success() {
        return success(null);
    }*/

    @SuppressWarnings("rawtypes")
    public static <T> ApiResult buildApiResult(Integer code, String message, T data) {
        ApiResult <T>apiResult = new ApiResult<>();

        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }
}
