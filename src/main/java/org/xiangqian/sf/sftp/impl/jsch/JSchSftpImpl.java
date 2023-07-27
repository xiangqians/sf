package org.xiangqian.sf.sftp.impl.jsch;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.xiangqian.sf.sftp.FileEntry;
import org.xiangqian.sf.sftp.Sftp;
import org.xiangqian.sf.ssh.impl.jsch.ChannelType;
import org.xiangqian.sf.ssh.impl.jsch.JSchSupport;
import org.xiangqian.sf.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiangqian
 * @date 01:13 2022/07/24
 */
@Slf4j
public class JSchSftpImpl extends JSchSupport implements Sftp {

    private ChannelSftp channel;

    public JSchSftpImpl(String host, int port, String user, String passwd, Duration timeout) throws JSchException {
        super(host, port, user, passwd, timeout);

        // ChannelSftp上传、下载文件
        channel = (ChannelSftp) openChannel(ChannelType.SFTP);

        // 通道连接
        channel.connect((int) timeout.toMillis());
        Assert.isTrue(channel.isConnected(), "channel连接失败");
    }

    @Override
    public List<FileEntry> ls(String path, Duration timeout) throws Exception {
        Vector<?> vector = channel.ls(path);
        return Optional.ofNullable(vector)
                .map(list -> list.stream().map(Object::toString).map(FileEntry::parse).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public void cd(String path, Duration timeout) throws Exception {
        channel.cd(path);
    }

    @Override
    public void mkdir(String path, Duration timeout) throws Exception {
        channel.mkdir(path);
    }

    @Override
    public void rm(String path, Duration timeout) throws Exception {
        try {
            // 删除普通文件
            channel.rm(path);
        } catch (Exception e) {
            // 删除目录文件（只允许删除空目录）
            channel.rmdir(path);
        }
    }

    @Override
    public InputStream put(String src, String dst, Duration timeout) throws Exception {
        channel.put(src, dst, new DefaultSftpProgressMonitor(), FileTransferMode.OVERWRITE.getValue());
        return null;
    }

    @Override
    public InputStream get(String src, String dst, Duration timeout) throws Exception {
        channel.get(src, dst, new DefaultSftpProgressMonitor(), FileTransferMode.OVERWRITE.getValue());
        return null;
    }

    @Override
    public void close() throws IOException {
        try {
            if (Objects.nonNull(channel)) {
                channel.disconnect();
                channel = null;
            }
        } finally {
            super.close();
        }
    }

}
