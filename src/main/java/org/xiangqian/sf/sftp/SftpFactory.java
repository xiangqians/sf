package org.xiangqian.sf.sftp;

import org.apache.commons.lang3.NotImplementedException;
import org.xiangqian.sf.sftp.impl.jsch.JschSftpImpl;
import org.xiangqian.sf.sftp.impl.sshd.SshdSftpImpl;
import org.xiangqian.sf.sftp.impl.sshj.SshjSftpImpl;
import org.xiangqian.sf.sftp.impl.vfs.VfsSftpImpl;

import java.time.Duration;

/**
 * sftp工厂
 *
 * @author xiangqian
 * @date 00:25 2023/07/28
 */
public class SftpFactory {

    public static Sftp get(SftpType type, String host, int port, String user, String passwd, Duration timeout) throws Exception {
        switch (type) {
            case JSCH:
                return new JschSftpImpl(host, port, user, passwd, timeout);

            case SSHJ:
                return new SshjSftpImpl(host, port, user, passwd, timeout);

            case SSHD:
                return new SshdSftpImpl(host, port, user, passwd, timeout);

            case VFS:
                return new VfsSftpImpl(host, port, user, passwd, timeout);

            default:
                throw new NotImplementedException(type.name());
        }
    }

}
