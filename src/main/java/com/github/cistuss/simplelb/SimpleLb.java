package com.github.cistuss.simplelb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleLb {

    public static void main(String[] args) {
        try (final ServerSocket server = new ServerSocket(80)) {
            final Socket socket = server.accept();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
                reader.lines().forEach(System.out::println);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
