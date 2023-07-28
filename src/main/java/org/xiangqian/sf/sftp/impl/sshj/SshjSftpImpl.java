package org.xiangqian.sf.sftp.impl.sshj;

import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.sftp.FileAttributes;
import net.schmizz.sshj.sftp.FileMode;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import org.xiangqian.sf.sftp.FileEntry;
import org.xiangqian.sf.sftp.Sftp;
import org.xiangqian.sf.ssh.impl.sshj.SshjSupport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xiangqian
 * @date 19:49 2023/07/28
 */
@Slf4j
public class SshjSftpImpl extends SshjSupport<SFTPClient> implements Sftp {

    public SshjSftpImpl(String host, int port, String user, String passwd, Duration timeout) throws IOException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public List<FileEntry> ls(String path, Duration timeout) throws Exception {
        List<RemoteResourceInfo> remoteResourceInfos = client.ls(path);
        return Optional.ofNullable(remoteResourceInfos)
                .map(list -> list.stream().map(remoteResourceInfo -> {
                    FileAttributes attrs = remoteResourceInfo.getAttributes();
                    FileMode mode = attrs.getMode();
                    FileEntry fileEntry = new FileEntry();
                    fileEntry.setType(attrs.getType().name());
                    fileEntry.setMod(mode.getMask() + "");
                    fileEntry.setCount(mode.getMask());
                    fileEntry.setOwner(attrs.getUID() + "");
                    fileEntry.setGroup(attrs.getGID() + "");
                    fileEntry.setSize(attrs.getSize());
                    fileEntry.setLastModifiedDate(null);
                    fileEntry.setName(remoteResourceInfo.getName());
                    return fileEntry;
                }).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
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
