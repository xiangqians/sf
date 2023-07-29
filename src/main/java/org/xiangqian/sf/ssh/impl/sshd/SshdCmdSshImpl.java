package org.xiangqian.sf.ssh.impl.sshd;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.util.Assert;
import org.xiangqian.sf.util.NoGenericException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 15:59 2023/07/29
 */
@Slf4j
public class SshdCmdSshImpl extends SshdSupport<SshClient> implements Ssh {

    public SshdCmdSshImpl(String host, int port, String user, String passwd, Duration timeout) throws IOException, NoGenericException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        ClientChannel channel = session.createExecChannel(cmd);
        channel.setErr(System.err);

        /**
         * {@link org.apache.sshd.client.channel.AbstractClientChannel#doWriteData(byte[], int, long)}
         * {@link org.apache.sshd.common.channel.AbstractChannel#handleData(org.apache.sshd.common.util.buffer.Buffer)}
         * {@link org.apache.sshd.common.session.helpers.AbstractConnectionService#channelData(org.apache.sshd.common.util.buffer.Buffer)}
         * {@link org.apache.sshd.common.session.helpers.AbstractConnectionService#process(int, org.apache.sshd.common.util.buffer.Buffer)}
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        channel.setOut(out);

        Assert.isTrue(channel.open().verify(timeout).isOpened(), "open failed");
        channel.waitFor(List.of(ClientChannelEvent.CLOSED), timeout);
        log.debug("\n{}", out);
//        return CleanableInputStream.create(null, channel::close);
        return null;
    }

}
