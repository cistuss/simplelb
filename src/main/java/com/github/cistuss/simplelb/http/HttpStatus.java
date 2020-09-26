package com.github.cistuss.simplelb.http;

public enum HttpStatus {
    // @formatter:off

    // 200 
    OK(200),

    // 300
    MOVED_PERMANENTLY(301), 
    FOUND(302),

    // 400
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    
    LENGTH_REQUIRED(411),

    // 502
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    ;

    private int code;

    HttpStatus(int code) { this.code = code; }
    public int getCode() { return code; }
    
    // @formatter:on
}
