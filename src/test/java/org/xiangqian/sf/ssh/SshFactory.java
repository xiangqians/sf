package org.xiangqian.sf.ssh;

import org.xiangqian.sf.ssh.impl.jsch.JschCmdSshImpl;
import org.xiangqian.sf.ssh.impl.jsch.JschShellSshImpl;
import org.xiangqian.sf.ssh.impl.sshj.SshjCmdSshImpl;
import org.xiangqian.sf.ssh.impl.sshj.SshjShellSshImpl;
import org.xiangqian.sf.util.Yaml;

import java.time.Duration;

/**
 * @author xiangqian
 * @date 00:16 2023/07/28
 */
public class SshFactory {

    public static Ssh create(Class<? extends Ssh> type) throws Exception {
        Yaml yaml = new Yaml("C:\\Users\\xiangqian\\Desktop\\tmp\\sf\\server.yml");
        String host = yaml.getString("server1.host");
        int port = yaml.getInt("server1.port");
        String user = yaml.getString("server1.user");
        String passwd = yaml.getString("server1.passwd");
        Duration timeout = Duration.ofSeconds(10);

        if (type == JschCmdSshImpl.class) {
            return new JschCmdSshImpl(host, port, user, passwd, timeout);
        }

        if (type == JschShellSshImpl.class) {
            return new JschShellSshImpl(host, port, user, passwd, timeout);
        }

        if (type == SshjCmdSshImpl.class) {
            return new SshjCmdSshImpl(host, port, user, passwd, timeout);
        }

        if (type == SshjShellSshImpl.class) {
            return new SshjShellSshImpl(host, port, user, passwd, timeout);
        }

        throw new UnsupportedOperationException();
    }

}
