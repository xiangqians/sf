package org.xiangqian.sf.ssh.impl.vfs;

import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.util.NoGenericException;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 16:42 2023/07/30
 */
public class VfsCmdSshImpl extends VfsSupport implements Ssh {

    public VfsCmdSshImpl(String host, int port, String user, String passwd, Duration timeout) throws NoGenericException, IOException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        return null;
    }

}
