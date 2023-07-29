package org.xiangqian.sf.ssh.impl.sshj;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.apache.commons.lang3.NotImplementedException;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;

/**
 * SSHJ
 * https://github.com/hierynomus/sshj
 * https://github.com/hierynomus/sshj/tree/master/examples/src/main/java/net/schmizz/sshj/examples
 *
 * @author xiangqian
 * @date 23:32 2023/07/27
 */
public abstract class SshjSupport<T extends Closeable> implements Closeable {

    protected T client;
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
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type type = actualTypeArguments[0];
//        System.out.println(type);

        // ssh
        if (type == SSHClient.class) {
            SSHClient sshClient = new SSHClient();
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(host, port);

            // only for password authentication
            sshClient.authPassword(user, passwd);
            // OR
            // only for public key authentication
//            sshClient.authPublickey("user", "location to private key file");

            client = (T) sshClient;
            session = sshClient.startSession();
        }
        // sftp
        else if (type == SFTPClient.class) {
            // ssh
            SSHClient sshClient = new SSHClient();
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(host, port);
            sshClient.authPassword(user, passwd);

            // sftp
            SFTPClient sftpClient = sshClient.newSFTPClient();
            client = (T) sftpClient;
        }
        // un
        else {
            throw new NotImplementedException();
        }
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(session, client);
    }

}
