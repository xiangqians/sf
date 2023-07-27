package org.xiangqian.sf.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 可清除输入流
 *
 * @author xiangqian
 * @date 11:40 2023/07/27
 */
@Slf4j
public class CleanableInputStream extends InputStream implements Runnable { // 设计有一个回收线程

    private InputStream in;
    private Closeable closeable;

    private CleanableInputStream(InputStream in, Closeable closeable) {
        this.in = in;
        this.closeable = closeable;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }

    @Override
    public int available() throws IOException {
        return in.available();
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(closeable, in);
    }

    @SneakyThrows
    @Override
    public String toString() {
        BufferedReader reader = null;
        try {
            byte[] buffer = readAllBytes();
            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer, 0, buffer.length), StandardCharsets.UTF_8));
            String line = null;
            StringBuilder builder = new StringBuilder();
            while (Objects.nonNull(line = reader.readLine())) {
                builder.append(line).append('\n');
            }
            return builder.toString();
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        log.debug("Cleaner触发清除");
        close();
    }

    public static CleanableInputStream create(InputStream in, Closeable closeable) {
        CleanableInputStream cleanableIn = new CleanableInputStream(in, closeable);
        new Cleaner(cleanableIn);
        return cleanableIn;
    }

}
