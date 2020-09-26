package com.github.cistuss.simplelb.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.cistuss.simplelb.util.StringUtils;

public class HttpRequestReader implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestReader.class);

    private static final String DEFAULT_ENCODING = "UTF-8";
    private final String encoding;
    private final InputStream inputStream;

    public HttpRequestReader(InputStream inputStream) {
        this(inputStream, DEFAULT_ENCODING);
    }

    public HttpRequestReader(InputStream socket, String encoding) {
        this.inputStream = socket;
        this.encoding = encoding;
    }

    public HttpRequest read() throws IOException {
        final HttpHeader header = readHeader(inputStream);
        final String body = readBody(inputStream, header);

        return new HttpRequest(header, body);
    }

    private HttpHeader readHeader(InputStream input) throws IOException {
        try {
            final HttpHeader header = new HttpHeader();
            String line = readLine(input);
            while (line != null && !line.isEmpty()) {
                final String[] pair = line.split(":");
                final String value = pair.length > 1 ? pair[1].trim() : "";
                header.add(pair[0].trim(), value);

                line = readLine(input);
            }
            return header;
        } catch (final IOException e) {
            throw new IOException("Failed to read header", e);
        }
    }

    private String readBody(InputStream input, HttpHeader header) {
        try {
            if (HttpRequest.isChunkTransfer(header)) {
                return readBodyByChunkTransfer(input, header);
            } else {
                return readBodyByContentLength(input, header);
            }
        } catch (final IOException e) {
            logger.error("Failed to read body: header: {}", header);
            throw new RuntimeException("Failed to read body", e);
        }
    }

    private String readBodyByContentLength(InputStream input, HttpHeader header) throws IOException {
        final String contentLength = header.getRaw("Content-Length");
        if (StringUtils.isNotEmpty(contentLength)) {
            final int length = Integer.parseInt(contentLength);
            final byte[] body = new byte[length];
            input.read(body);
            return new String(body, encoding);
        }
        return "";
    }

    private String readBodyByChunkTransfer(InputStream input, HttpHeader header) throws IOException {

        final StringBuilder body = new StringBuilder();

        int chunkSize = Integer.parseInt(readLine(input), 16);
        while (chunkSize != 0) {
            final byte[] buffer = new byte[chunkSize];
            input.read(buffer);
            body.append(new String(buffer, encoding));

            readLine(input); // skip reading CRLF 
            chunkSize = Integer.parseInt(readLine(input), 16);
        }
        return body.toString();
    }

    private String readLine(InputStream in) throws IOException {
        final List<Byte> list = new ArrayList<>();

        while (true) {
            final byte b = (byte) in.read();

            if (b == -1) {
                throw new RuntimeException("Empty request");
            }

            list.add(b);

            final int size = list.size();
            if (2 <= size) {
                final char cr = (char) list.get(size - 2).byteValue();
                final char lf = (char) list.get(size - 1).byteValue();

                if (cr == '\r' && lf == '\n') {
                    break;
                }
            }
        }

        final byte[] buffer = new byte[list.size() - 2]; // CRLF の分減らす
        for (int i = 0; i < list.size() - 2; i++) {
            buffer[i] = list.get(i);
        }

        return new String(buffer, encoding);
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
