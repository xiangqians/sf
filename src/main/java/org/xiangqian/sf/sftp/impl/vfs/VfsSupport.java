package org.xiangqian.sf.sftp.impl.vfs;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.xiangqian.sf.util.NoGenericException;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

/**
 * Commons VFS
 * https://commons.apache.org/proper/commons-vfs/
 * https://github.com/apache/commons-vfs
 *
 * @author xiangqian
 * @date 16:40 2023/07/30
 */
public class VfsSupport implements Closeable {

    protected StandardFileSystemManager fileSystemManager;
    protected FileObject fileObject;

    /**
     * 构造函数
     *
     * @param host       服务器地址
     * @param port       服务器端口
     * @param user       用户
     * @param passwd     密码
     * @param privateKey 私钥
     * @param timeout    连接超时时间
     * @throws NoGenericException
     * @throws IOException
     */
    protected VfsSupport(String host, int port, String user, String passwd, File privateKey, Duration timeout) throws FileSystemException {
        fileSystemManager = new StandardFileSystemManager();
        fileSystemManager.init();

        SftpFileSystemConfigBuilder sftpFileSystemConfigBuilder = SftpFileSystemConfigBuilder.getInstance();
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        sftpFileSystemConfigBuilder.setUserDirIsRoot(fileSystemOptions, false);
        sftpFileSystemConfigBuilder.setStrictHostKeyChecking(fileSystemOptions, "yes");

        // 支持服务器身份验证，设置 known_host 文件位置
//        sftpFileSystemConfigBuilder.setKnownHosts(opts, new File("~/.ssh/known_hosts"));

        // 设置私钥
        if (Objects.nonNull(privateKey)) {
            sftpFileSystemConfigBuilder.setIdentities(fileSystemOptions, privateKey);
        }

        String uri = null;
        // public key authentication
        if (Objects.nonNull(privateKey)) {
            uri = String.format("sftp://%s@%s", user, host);
        }
        // password authentication
        else if (Objects.nonNull(passwd)) {
            uri = String.format("sftp://%s:%s@%s", user, passwd, host);
        } else {
            throw new RuntimeException("Either privateKey nor password is set. Please call one of the auth methods.");
        }

        fileObject = fileSystemManager.resolveFile(uri, fileSystemOptions);
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(fileObject)) {
            IOUtils.closeQuietly(fileObject);
            fileObject = null;
        }

        if (Objects.nonNull(fileSystemManager)) {
            fileSystemManager.close();
            fileSystemManager = null;
        }
    }

}
