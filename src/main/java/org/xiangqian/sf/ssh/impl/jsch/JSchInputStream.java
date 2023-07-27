package org.xiangqian.sf.ssh.impl.jsch;

import com.jcraft.jsch.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author xiangqian
 * @date 11:40 2023/07/27
 */
@Slf4j
public class JSchInputStream extends InputStream implements Runnable { // 设计有一个回收线程

    private Channel channel;
    private InputStream in;

    public JSchInputStream(Channel channel, InputStream in) {
        this.channel = channel;
        this.in = in;
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
        if (Objects.nonNull(channel)) {
            channel.disconnect();
            channel = null;
        }
        if (Objects.nonNull(in)) {
            IOUtils.closeQuietly(in);
            in = null;
        }
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

}
