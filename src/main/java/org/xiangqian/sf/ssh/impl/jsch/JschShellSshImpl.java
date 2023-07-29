package org.xiangqian.sf.ssh.impl.jsch;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.xiangqian.sf.ssh.Ssh;

import java.io.InputStream;
import java.time.Duration;

/**
 * 执行命令（可执行单行、多行命令）
 *
 * @author xiangqian
 * @date 22:37 2022/07/25
 */
@Slf4j
public class JschShellSshImpl extends JschSupport implements Ssh {

    public JschShellSshImpl(String host, int port, String user, String passwd, Duration timeout) throws JSchException {
        super(host, port, user, passwd, timeout);
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        // ChannelShell执行命令（可执行多行命令）
        ChannelShell channel = (ChannelShell) openChannel(ChannelType.SHELL);

        return null;
    }

}