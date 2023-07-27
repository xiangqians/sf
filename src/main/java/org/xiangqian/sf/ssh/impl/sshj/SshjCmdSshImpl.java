package org.xiangqian.sf.ssh.impl.sshj;

import net.schmizz.sshj.connection.channel.direct.Session;
import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.util.CleanableInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 23:30 2023/07/27
 */
public class SshjCmdSshImpl extends SshjSupport implements Ssh {

    public SshjCmdSshImpl(String host, int port, String user, String passwd, Duration timeout) throws IOException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        Session.Command command = session.exec(cmd);
        return CleanableInputStream.create(command.getInputStream(), command::close);
    }

}
