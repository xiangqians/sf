package org.xiangqian.sf.sftp.impl.vfs;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.xiangqian.sf.sftp.FileEntry;
import org.xiangqian.sf.sftp.Sftp;
import org.xiangqian.sf.util.NoGenericException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 16:42 2023/07/30
 */
public class VfsSftpImpl extends VfsSupport implements Sftp {

    public VfsSftpImpl(String host, int port, String user, String passwd, Duration timeout) throws NoGenericException, IOException {
        super(host, port, user, passwd, null, timeout);
    }

    @Override
    public List<FileEntry> ls(String path, Duration timeout) throws Exception {
        FileObject fileObject = null;
        try {
            fileObject = super.fileObject.resolveFile(path);
            FileObject[] children = fileObject.getChildren();
            for (FileObject child : children) {
                System.out.println(child.getName().getBaseName());
            }
        } finally {
            IOUtils.closeQuietly(fileObject);
        }
        return null;
    }

    @Override
    public void cd(String path, Duration timeout) throws Exception {

    }

    @Override
    public void mkdir(String path, Duration timeout) throws Exception {

    }

    @Override
    public void rm(String path, Duration timeout) throws Exception {

    }

    @Override
    public InputStream put(String src, String dst, Duration timeout) throws Exception {
        return null;
    }

    @Override
    public InputStream put(InputStream src, String dst, Duration timeout) throws Exception {
        return null;
    }

    @Override
    public InputStream get(String src, String dst, Duration timeout) throws Exception {
        return null;
    }

    @Override
    public InputStream get(String src, OutputStream dst, Duration timeout) throws Exception {
        return null;
    }

}
