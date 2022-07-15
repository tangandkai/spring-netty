package com.tang.rpc.protocol;

import com.tang.rpc.serialization.HessianSerialization;

/**
 * rpc消息响应
 * @date 2021-05-26 00:53
 * @author tangwenkai
 */
public class Response extends HessianSerialization {

    /**
     * 响应的错误码，正常响应为0，非0表示异常响应
     */
    private int code = 0;

    /**
     * 异常信息
     */
    private String errMsg;

    /**
     * 响应结果
     */
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
