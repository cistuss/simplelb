package com.github.cistuss.simplelb.http;

import java.util.Optional;

public class HttpRequest {
    private final HttpHeader header;
    private final String body;

    public HttpRequest(HttpHeader header, String body) {
        this.header = header;
        this.body = body;
    }

    public HttpHeader getHeaders() {
        return header;
    }

    public Optional<String> getHeader(String name) {
        return header.get(name);
    }

    public String getBody() {
        return body;
    }

    public static boolean isChunkTransfer(HttpHeader header) {
        return header.get("Transfer-Encoding").map(transfer -> {
            return "chunked".equals(transfer);
        }).orElse(false);
    }
}
