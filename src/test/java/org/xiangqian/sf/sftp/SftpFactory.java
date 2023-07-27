package org.xiangqian.sf.sftp;

import org.xiangqian.sf.sftp.impl.jsch.JschSftpImpl;
import org.xiangqian.sf.util.Yaml;

import java.time.Duration;

/**
 * @author xiangqian
 * @date 00:25 2023/07/28
 */
public class SftpFactory {

    public static Sftp create(Class<? extends Sftp> type) throws Exception {
        Yaml yaml = new Yaml("C:\\Users\\xiangqian\\Desktop\\tmp\\sf\\server.yml");
        String host = yaml.getString("server1.host");
        int port = yaml.getInt("server1.port");
        String user = yaml.getString("server1.user");
        String passwd = yaml.getString("server1.passwd");
        Duration timeout = Duration.ofSeconds(10);

        if (type == JschSftpImpl.class) {
            return new JschSftpImpl(host, port, user, passwd, timeout);
        }

        throw new UnsupportedOperationException();
    }

}
