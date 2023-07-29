package org.xiangqian.sf.ssh;

import org.apache.commons.lang3.NotImplementedException;
import org.xiangqian.sf.ssh.impl.jsch.JschCmdSshImpl;
import org.xiangqian.sf.ssh.impl.jsch.JschShellSshImpl;
import org.xiangqian.sf.ssh.impl.sshd.SshdCmdSshImpl;
import org.xiangqian.sf.ssh.impl.sshj.SshjCmdSshImpl;
import org.xiangqian.sf.ssh.impl.sshj.SshjShellSshImpl;

import java.time.Duration;

/**
 * ssh工厂
 *
 * @author xiangqian
 * @date 00:16 2023/07/28
 */
public class SshFactory {

    public static Ssh get(SshType type, String host, int port, String user, String passwd, Duration timeout) throws Exception {
        switch (type) {
            case JSCH_CMD:
                return new JschCmdSshImpl(host, port, user, passwd, timeout);

            case JSCH_SHELL:
                return new JschShellSshImpl(host, port, user, passwd, timeout);

            case SSHJ_CMD:
                return new SshjCmdSshImpl(host, port, user, passwd, timeout);

            case SSHJ_SHELL:
                return new SshjShellSshImpl(host, port, user, passwd, timeout);

            case SSHD_CMD:
                return new SshdCmdSshImpl(host, port, user, passwd, timeout);

            case SSHD_SHELL:

            default:
                throw new NotImplementedException(type.name());
        }
    }

}
