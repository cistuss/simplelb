package com.github.cistuss.simplelb.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cistuss.simplelb.exception.ServerException;
import com.github.cistuss.simplelb.exception.ServerStartFailException;

public class HttpServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    protected static final int DEFAULT_POOL_SIZE = 10;

    private static final String CRLF = "\r\n";

    private final ServerSocket serverSocket;
    private final ExecutorService threadPool;

    public HttpServer(int port) {
        this(port, DEFAULT_POOL_SIZE);
    }

    public HttpServer(int port, int poolSize) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.threadPool = Executors.newFixedThreadPool(poolSize);
        } catch (final IOException e) {
            throw new ServerStartFailException("Failed to instantiate server.", e);
        }
    }

    public final void start() {
        logger.info(">> Start Http Server");

        try {
            while (true) {
                final Socket socket = serverSocket.accept();
                threadPool.execute(() -> serverProcess(socket));
            }
        } catch (final IOException e) {
            threadPool.shutdown();
        }
        //        logger.info(">> END Http Server");
    }

    private void serverProcess(Socket socket) {
        try (HttpRequestReader reader = new HttpRequestReader(socket.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"))) {

            final HttpRequest request = reader.read();
            logger.debug("recieving request");
            logger.debug("headers: {}", request.getHeaders());

            writer.write("HTTP/1.1 200 OK" + CRLF);
            writer.write("Content-Type: text/html" + CRLF);
            writer.write(CRLF);
            writer.write(request.getBody());
            writer.write(CRLF);
        } catch (final IOException e) {
            throw new ServerException(e);
        }
    }

    protected int getThreadMaxCount() {
        return DEFAULT_POOL_SIZE;
    }
}
