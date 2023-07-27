package org.xiangqian.sf.ssh.impl.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.util.Assert;
import org.xiangqian.sf.util.Cleaner;

import java.io.InputStream;
import java.time.Duration;
import java.util.Objects;

/**
 * 执行单行命令
 *
 * @author xiangqian
 * @date 13:11 2022/07/23
 */
@Slf4j
public class JschExecSshImpl extends JschSupport implements Ssh {

    public JschExecSshImpl(String host, int port, String user, String passwd, Duration timeout) throws JSchException {
        super(host, port, user, passwd, timeout);
    }

    @Override
    public InputStream exec(String cmd, Duration timeout) throws Exception {
        Assert.notEmpty(cmd = StringUtils.trim(cmd), "命令不能为空");
        ChannelExec channel = null;
        InputStream in = null;
        try {
            // 打开一个执行通道
            // ChannelExec执行单行命令
            channel = (ChannelExec) openChannel(ChannelType.EXEC);
            channel.setErrStream(System.err);

            // 获取输入流
            in = channel.getInputStream();

            // 设置命令
            channel.setCommand(cmd);

            // 连接并执行命令
            // 设置com.jcraft.jsch.Channel.sendChannelOpen
            /**
             * {@link Channel#sendChannelOpen()}
             * 设置指定时间，会导致 retry = 1，默认是 retry = 2000
             * 所以，此处便不设置channel timeout
             */
//            channel.connect((int) timeout.toMillis());
            channel.connect();
            Assert.isTrue(channel.isConnected(), "channel连接失败");

            JschInputStream jSchIn = new JschInputStream(channel, in);
            new Cleaner(jSchIn);
            return jSchIn;
        } catch (Exception e) {
            if (Objects.nonNull(channel)) {
                channel.disconnect();
            }
            IOUtils.closeQuietly(in);
            throw e;
        }
    }

}
