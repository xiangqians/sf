package org.xiangqian.sf.ssh;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.ssh.impl.sshj.SshjCmdSshImpl;

import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 14:00 2022/07/23
 */
@Slf4j
public class SshTest {

    @Test
    public void cmd() throws Exception {
        Ssh ssh = null;
        InputStream in = null;
        try {
//            ssh = SshFactory.create(JschCmdSshImpl.class);
            ssh = SshFactory.create(SshjCmdSshImpl.class);
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


}
