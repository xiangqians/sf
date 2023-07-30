package org.xiangqian.sf.sftp.impl.vfs;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.xiangqian.sf.sftp.FileEntry;
import org.xiangqian.sf.sftp.Sftp;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xiangqian
 * @date 16:42 2023/07/30
 */
public class VfsSftpImpl extends VfsSupport implements Sftp {

    public VfsSftpImpl(String host, int port, String user, String passwd, File privateKey, File knownHosts, Duration timeout) throws FileSystemException {
        super(host, port, user, passwd, privateKey, knownHosts, timeout);
    }

    @Override
    public List<FileEntry> ls(String path, Duration timeout) throws Exception {
        FileObject fo = null;
        try {
            fo = fileObject.resolveFile(path);
            FileObject[] children = fo.getChildren();
            return Optional.ofNullable(children)
                    .filter(ArrayUtils::isNotEmpty)
                    .map(Arrays::asList)
                    .map(list -> list.stream().map(child -> {
                        FileEntry fileEntry = new FileEntry();
                        try {
                            fileEntry.setType(child.getType().getName());
                        } catch (FileSystemException e) {
                            e.printStackTrace();
                        }
                        fileEntry.setMod(null);
                        fileEntry.setCount(null);
                        fileEntry.setOwner(null);
                        fileEntry.setGroup(null);
                        fileEntry.setSize(0);
                        fileEntry.setLastModifiedDate(null);
                        fileEntry.setName(child.getName().getBaseName());
                        return fileEntry;
                    }).collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        } finally {
            IOUtils.closeQuietly(fo);
        }
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
