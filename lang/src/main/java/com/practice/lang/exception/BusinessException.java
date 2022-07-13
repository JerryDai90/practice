package com.practice.lang.exception;

//提示性异常
public class BusinessException extends RuntimeException {
    //异常代码
    private String code;
    //微服务追踪id
    private String tracksId;
    //微服务所在机器实例id
    private String serverId;
    public BusinessException(String message, Throwable cause, String code, String tracksId, String serverId) {
        super(message, cause);
        this.code = code;
        this.tracksId = tracksId;
        this.serverId = serverId;
    }
}
