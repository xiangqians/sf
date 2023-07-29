package org.xiangqian.sf.ssh.impl.sshd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.xiangqian.sf.util.Assert;
import org.xiangqian.sf.util.NoGenericException;
import org.xiangqian.sf.util.ReflectionUtil;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Objects;

/**
 * Apache MINA SSHD
 * https://mina.apache.org/sshd-project/documentation.html
 * https://github.com/apache/mina-sshd
 *
 * @author xiangqian
 * @date 15:49 2023/07/29
 */
@Slf4j
public abstract class SshdSupport<T extends Closeable> implements Closeable {

    protected T client;
    protected ClientSession session;

    /**
     * 构造函数
     *
     * @param host    服务器地址
     * @param port    服务器端口
     * @param user    用户
     * @param passwd  密码
     * @param timeout 连接超时时间
     */
    protected SshdSupport(String host, int port, String user, String passwd, Duration timeout) throws NoGenericException, IOException {
        Type type = ReflectionUtil.getSuperClassGenericType(this.getClass(), 0);
        SshClient sshClient = null;
        try {
            // ssh client
            sshClient = SshClient.setUpDefaultClient();
            sshClient.start();

            // session
            session = sshClient.connect(user, host, port).verify(timeout).getSession();
            // password authentication
            session.addPasswordIdentity(passwd);
            // public key authentication
//        session.addPublicKeyIdentity();

            Assert.isTrue(session.auth().verify(timeout).isSuccess(), "auth failed");

            // ssh
            if (type == SshClient.class) {
                client = (T) sshClient;
            }
            // sftp
            else if (type == SftpClient.class) {
                SftpClient sftpClient = SftpClientFactory.instance().createSftpClient(session);
                client = (T) sftpClient;
            }
            // not implemented
            else {
                throw new NotImplementedException();
            }
        } catch (Exception e) {
            if (Objects.nonNull(sshClient)) {
                try {
                    sshClient.stop();
                } finally {
                    IOUtils.closeQuietly(sshClient);
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(session)) {
            IOUtils.closeQuietly(session);
            session = null;
        }

        if (Objects.nonNull(client)) {
            try {
                if (client instanceof SshClient) {
                    SshClient sshClient = (SshClient) client;
                    sshClient.stop();
                }
            } finally {
                IOUtils.closeQuietly(client);
                client = null;
            }
        }
    }

}
