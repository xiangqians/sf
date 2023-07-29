package org.xiangqian.sf.sftp.impl.sshd;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.sshd.sftp.client.SftpClient;
import org.xiangqian.sf.sftp.FileEntry;
import org.xiangqian.sf.sftp.Sftp;
import org.xiangqian.sf.ssh.impl.sshd.SshdSupport;
import org.xiangqian.sf.util.NoGenericException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 17:39 2023/07/29
 */
public class SshdSftpImpl extends SshdSupport<SftpClient> implements Sftp {

    public SshdSftpImpl(String host, int port, String user, String passwd, Duration timeout) throws NoGenericException, IOException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public List<FileEntry> ls(String path, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public void cd(String path, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public void mkdir(String path, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public void rm(String path, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public InputStream put(String src, String dst, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public InputStream put(InputStream src, String dst, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public InputStream get(String src, String dst, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

    @Override
    public InputStream get(String src, OutputStream dst, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

}
