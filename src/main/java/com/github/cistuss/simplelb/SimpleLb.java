package com.github.cistuss.simplelb;

import com.github.cistuss.simplelb.http.HttpServer;

public class SimpleLb extends HttpServer {

    public static void main(String[] args) {
        final SimpleLb simpleLb = new SimpleLb(80);
        simpleLb.start();
    }

    public SimpleLb(int port) {
        super(port);
    }
}
