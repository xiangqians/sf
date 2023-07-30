package org.xiangqian.sf.ssh.impl.vfs;

import org.xiangqian.sf.util.NoGenericException;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;

/**
 * Commons VFS
 *
 * @author xiangqian
 * @date 16:40 2023/07/30
 */
public class VfsSupport implements Closeable {

    /**
     * 构造函数
     *
     * @param host    服务器地址
     * @param port    服务器端口
     * @param user    用户
     * @param passwd  密码
     * @param timeout 连接超时时间
     */
    protected VfsSupport(String host, int port, String user, String passwd, Duration timeout) throws NoGenericException, IOException {
    }

    @Override
    public void close() throws IOException {

    }
}
