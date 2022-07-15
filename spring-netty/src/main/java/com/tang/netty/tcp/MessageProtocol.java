package com.tang.netty.tcp;

/**
 * 定义协议
 * @author tangwenkai
 * @date 2021-06-23 20:12
 */
public class MessageProtocol {

    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
