package org.xiangqian.sf.ssh.impl.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.xiangqian.sf.util.Assert;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

/**
 * JSch
 * http://www.jcraft.com/jsch/
 * http://www.jcraft.com/jsch/examples/
 * https://github.com/is/jsch
 * <p>
 * 第三方JSch（第三方维护的版本）
 * https://github.com/mwiede/jsch
 *
 * @author xiangqian
 * @date 13:50 2022/07/23
 */
public abstract class JschSupport implements Closeable {

    protected Session session;

    /**
     * 构造函数
     *
     * @param host    服务器地址
     * @param port    服务器端口
     * @param user    用户
     * @param passwd  密码
     * @param timeout 连接超时时间
     */
    protected JschSupport(String host, int port, String user, String passwd, Duration timeout) throws JSchException {
        JSch jsch = new JSch();

        // 支持服务器身份验证，设置 known_host 文件位置
//        jsch.setKnownHosts();

        // public key authentication
//        jsch.addIdentity("location to private key file");

        // 设置服务器地址、端口、用户名、密码
        session = jsch.getSession(user, host, port);
        // password authentication
        session.setPassword(passwd);
        // 跳过公钥检测
        session.setConfig("StrictHostKeyChecking", "no");

        // 连接到服务器
        session.connect((int) timeout.toMillis());
        Assert.isTrue(session.isConnected(), "session连接失败");
    }

    protected Channel openChannel(ChannelType type) throws JSchException {
        Assert.isTrue(session.isConnected(), "session未连接");
        return session.openChannel(type.getValue());
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(session)) {
            try {
                session.disconnect();
            } finally {
                session = null;
            }
        }
    }

}
