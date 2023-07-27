package org.xiangqian.sf.ssh;

import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.common.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.ssh.impl.sshj.SshjCmdSshImpl;

import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 23:26 2023/07/27
 */
@Slf4j
public class SshjSshTest {

    @Test
    public void exec() throws Exception {
        Server server = Server.get();
        Ssh ssh = null;
        InputStream in = null;
        try {
            ssh = new SshjCmdSshImpl(server.getHost(), server.getPort(), server.getUser(), server.getPasswd(), server.getTimeout());
            in = ssh.exec("ls -l", Duration.ofSeconds(10));
            log.debug("\n{}", in);
            System.gc();
        } finally {
            IOUtils.closeQuietly(in, ssh);
        }
    }

}
