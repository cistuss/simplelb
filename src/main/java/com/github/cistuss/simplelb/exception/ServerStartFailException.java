package com.github.cistuss.simplelb.exception;

public class ServerStartFailException extends ServerException {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE = "Failed to start server";

    public ServerStartFailException(String message) {
        super(message);
    }

    public ServerStartFailException(String message, Throwable e) {
        super(message, e);
    }

    public ServerStartFailException(Throwable e) {
        super(DEFAULT_MESSAGE, e);
    }
}
