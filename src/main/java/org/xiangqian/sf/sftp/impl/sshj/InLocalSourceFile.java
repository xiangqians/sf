package org.xiangqian.sf.sftp.impl.sshj;

import lombok.SneakyThrows;
import net.schmizz.sshj.xfer.LocalFileFilter;
import net.schmizz.sshj.xfer.LocalSourceFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiangqian
 * @date 15:44 2023/07/30
 */
public class InLocalSourceFile implements LocalSourceFile {

    private InputStream in;

    public InLocalSourceFile(InputStream in) {
        this.in = in;
    }

    @Override
    public String getName() {
        return null;
    }

    @SneakyThrows
    @Override
    public long getLength() {
        return in.available();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return in;
    }

    @Override
    public int getPermissions() throws IOException {
        return 0;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public Iterable<? extends LocalSourceFile> getChildren(LocalFileFilter localFileFilter) throws IOException {
        return null;
    }

    @Override
    public boolean providesAtimeMtime() {
        return false;
    }

    @Override
    public long getLastAccessTime() throws IOException {
        return 0;
    }

    @Override
    public long getLastModifiedTime() throws IOException {
        return 0;
    }

}
