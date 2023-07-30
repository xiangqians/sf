package org.xiangqian.sf.sftp.impl.vfs;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.xiangqian.sf.util.Assert;
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
     * @param privateKey 私钥文件
     * @param knownHosts known_host文件
     * @param timeout    连接超时时间
     * @throws NoGenericException
     * @throws IOException
     */
    protected VfsSupport(String host, int port, String user, String passwd, File privateKey, File knownHosts, Duration timeout) throws FileSystemException {
        host = StringUtils.trim(host);
        user = StringUtils.trim(user);
        passwd = StringUtils.trim(passwd);
        Assert.isTrue(StringUtils.isNotEmpty(passwd) || Objects.nonNull(privateKey), "passwd或privateKey是必须的");
        Assert.notNull(knownHosts, "必须指定known_host文件");

        fileSystemManager = new StandardFileSystemManager();
        fileSystemManager.init();

        SftpFileSystemConfigBuilder sftpFileSystemConfigBuilder = SftpFileSystemConfigBuilder.getInstance();
        FileSystemOptions fileSystemOptions = new FileSystemOptions();
        sftpFileSystemConfigBuilder.setUserDirIsRoot(fileSystemOptions, false);
        sftpFileSystemConfigBuilder.setStrictHostKeyChecking(fileSystemOptions, "yes");

        // 支持服务器身份验证，设置 known_host 文件位置
        // ~/.ssh/known_hosts
        sftpFileSystemConfigBuilder.setKnownHosts(fileSystemOptions, knownHosts);

        String uri = null;

        // password authentication
        if (StringUtils.isNotEmpty(passwd)) {
            uri = String.format("sftp://%s:%s@%s:%s", user, passwd, host, port);
        }
        // public key authentication
        else {
            uri = String.format("sftp://%s@%s:%s", user, host, port);

            // 设置私钥
            sftpFileSystemConfigBuilder.setIdentities(fileSystemOptions, privateKey);
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
