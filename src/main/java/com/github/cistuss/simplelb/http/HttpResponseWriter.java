package com.github.cistuss.simplelb.http;

import java.io.OutputStream;

public class HttpResponseWriter implements AutoCloseable {

    private final OutputStream outputStream;

    public HttpResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(HttpResponse response) {
    }

    @Override
    public void close() throws Exception {

    }
}
