package com.practice.lang.exception;

//逻辑异常
public class LogicalException extends RuntimeException {
    //异常代码
    private String code;
    //微服务追踪id
    private String tracksId;
    //微服务所在机器实例id
    private String serverId;
    public LogicalException(String message, Throwable cause, String code, String tracksId, String serverId) {
        super(message, cause);
        this.code = code;
        this.tracksId = tracksId;
        this.serverId = serverId;
    }
}
