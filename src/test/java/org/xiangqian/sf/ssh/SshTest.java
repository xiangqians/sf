package org.xiangqian.sf.ssh;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.AbsTest;

import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 14:00 2022/07/23
 */
@Slf4j
public class SshTest extends AbsTest {

    @Test
    public void cmd() throws Exception {
        Ssh ssh = null;
        InputStream in = null;
        try {
            ssh = getSsh(SshType.SSHD);
            in = ssh.exec("ls -l", Duration.ofSeconds(10));
            log.debug("\n{}", in);
            System.gc();
        } finally {
            IOUtils.closeQuietly(in, ssh);
        }
    }

    @Test
    public void shell() throws Exception {

    }

    public static Ssh getSsh(SshType type) throws Exception {
        Server server = Server.get();
        String host = server.getHost();
        int port = server.getPort();
        String user = server.getUser();
        String passwd = server.getPasswd();
        Duration timeout = server.getTimeout();
        Ssh ssh = SshFactory.get(type, host, port, user, passwd, timeout);
        log.debug("ssh impl: {}", ssh.getClass());
        return ssh;
    }

}
