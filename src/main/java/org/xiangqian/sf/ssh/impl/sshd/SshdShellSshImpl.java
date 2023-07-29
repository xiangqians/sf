package org.xiangqian.sf.ssh.impl.sshd;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.sshd.client.SshClient;
import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.util.NoGenericException;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 16:49 2023/07/29
 */
public class SshdShellSshImpl extends SshdSupport<SshClient> implements Ssh {

    public SshdShellSshImpl(String host, int port, String user, String passwd, Duration timeout) throws IOException, NoGenericException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        throw new NotImplementedException();
    }

}
