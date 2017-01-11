package com.jackfruit.mall.bean;

import java.io.Serializable;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.bean
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 14:44
 * @修改
 * @修改时期 2016/12/27 14:44
 */
public class HttpResult<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
