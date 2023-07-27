package org.xiangqian.sf.ssh.impl.sshj;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;

/**
 * SSHJ
 * https://github.com/hierynomus/sshj
 * https://github.com/hierynomus/sshj/tree/master/examples/src/main/java/net/schmizz/sshj/examples
 *
 * @author xiangqian
 * @date 23:32 2023/07/27
 */
public abstract class SshjSupport implements Closeable {

    private SSHClient sshClient;
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
    protected SshjSupport(String host, int port, String user, String passwd, Duration timeout) throws IOException {
        sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect(host, port);
        sshClient.authPassword(user, passwd);
        session = sshClient.startSession();
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(session, sshClient);
    }

}
