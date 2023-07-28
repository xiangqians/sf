package org.xiangqian.sf.ssh;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 21:03 2023/07/28
 */
@Slf4j
public class HelloWorld {

    public static void main(String[] args) throws Exception {
        Ssh ssh = null;
        InputStream in = null;
        try {
            // 服务器地址
            String host = "host";
            // 服务器端口
            int port = 22;
            // 用户
            String user = "user";
            // 密码
            String passwd = "passwd";
            // 连接超时时间
            Duration timeout = Duration.ofSeconds(30);

            // 获取ssh实例
            ssh = SshFactory.get(SshType.JSCH_CMD, host, port, user, passwd, timeout);

            // 执行命令
            in = ssh.exec("ls -l", Duration.ofSeconds(10));
            log.debug("\n{}", in);
        } finally {
            IOUtils.closeQuietly(in, ssh);
        }
    }

}
