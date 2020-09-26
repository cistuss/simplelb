package com.github.cistuss.simplelb.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpHeader {
    private final Map<String, String> headers;

    public HttpHeader() {
        this.headers = new HashMap<>();
    }

    public void add(String name, String value) {
        headers.put(name, value);
    }

    public Optional<String> get(String name) {
        return Optional.ofNullable(headers.get(name));
    }

    public String getRaw(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        return headers.toString();
    }
}
