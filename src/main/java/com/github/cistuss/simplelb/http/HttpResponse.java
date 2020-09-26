package com.github.cistuss.simplelb.http;

import java.util.Optional;

public class HttpResponse {

    private final HttpHeader header;
    private HttpStatus status;
    private String body;

    public HttpResponse() {
        header = new HttpHeader();
    }

    public void addHeader(String name, String value) {
        header.add(name, value);
    }

    public Optional<String> getHeader(String name) {
        return header.get(name);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
