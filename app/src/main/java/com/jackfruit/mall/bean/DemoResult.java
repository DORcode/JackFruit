package com.jackfruit.mall.bean;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.bean
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 15:53
 * @修改
 * @修改时期 2016/12/27 15:53
 */

public class DemoResult<T> {
    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
