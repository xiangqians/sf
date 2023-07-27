package org.xiangqian.sf.ssh;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.ssh.impl.jsch.JschExecSshImpl;

import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 14:00 2022/07/23
 */
@Slf4j
public class JschSshTest {

    @Test
    public void exec() throws Exception {
        Server server = Server.get();
        Ssh ssh = null;
        InputStream in = null;
        try {
            ssh = new JschExecSshImpl(server.getHost(), server.getPort(), server.getUser(), server.getPasswd(), server.getTimeout());
            in = ssh.exec("ls -l", Duration.ofSeconds(10));
            log.debug("\n{}", in);
            System.gc();
        } finally {
            IOUtils.closeQuietly(ssh, in);
        }
    }

}
