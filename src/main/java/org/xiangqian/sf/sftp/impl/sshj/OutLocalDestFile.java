package org.xiangqian.sf.sftp.impl.sshj;

import net.schmizz.sshj.xfer.LocalDestFile;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author xiangqian
 * @date 15:47 2023/07/30
 */
public class OutLocalDestFile implements LocalDestFile {

    private OutputStream out;

    public OutLocalDestFile(OutputStream out) {
        this.out = out;
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream getOutputStream(boolean append) throws IOException {
        return out;
    }

    @Override
    public LocalDestFile getChild(String name) {
        return null;
    }

    @Override
    public LocalDestFile getTargetFile(String fileName) throws IOException {
        return null;
    }

    @Override
    public LocalDestFile getTargetDirectory(String dirName) throws IOException {
        return null;
    }

    @Override
    public void setPermissions(int perms) throws IOException {
    }

    @Override
    public void setLastAccessedTime(long t) throws IOException {
    }

    @Override
    public void setLastModifiedTime(long t) throws IOException {
    }

}
