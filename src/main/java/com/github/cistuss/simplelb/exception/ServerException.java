package com.github.cistuss.simplelb.exception;

public class ServerException extends RuntimeException {

    /** Serialize UID */
    private static final long serialVersionUID = 1L;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable e) {
        super(message, e);
    }

    public ServerException(Throwable e) {
        super("Server Exception occured", e);
    }
}
