package org.xiangqian.sf.ssh.impl.sshj;

import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.util.NoGenericException;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 00:02 2023/07/28
 */
public class SshjShellSshImpl extends SshjSupport implements Ssh {

    public SshjShellSshImpl(String host, int port, String user, String passwd, Duration timeout) throws NoGenericException, IOException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        return null;
    }

}
