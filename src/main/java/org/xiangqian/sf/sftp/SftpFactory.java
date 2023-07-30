package org.xiangqian.sf.sftp;

import org.apache.commons.lang3.NotImplementedException;
import org.xiangqian.sf.sftp.impl.jsch.JschSftpImpl;
import org.xiangqian.sf.sftp.impl.sshd.SshdSftpImpl;
import org.xiangqian.sf.sftp.impl.sshj.SshjSftpImpl;
import org.xiangqian.sf.sftp.impl.vfs.VfsSftpImpl;

import java.io.File;
import java.time.Duration;

/**
 * sftp工厂
 *
 * @author xiangqian
 * @date 00:25 2023/07/28
 */
public class SftpFactory {

    public static Sftp get(SftpType type, String host, int port, String user, File publicKey, Duration timeout) throws Exception {
        return get(type, host, port, user, publicKey, null, timeout);
    }

    public static Sftp get(SftpType type, String host, int port, String user, File publicKey, File knownHosts, Duration timeout) throws Exception {
        return get(type, host, port, user, null, publicKey, knownHosts, timeout);
    }

    public static Sftp get(SftpType type, String host, int port, String user, String passwd, Duration timeout) throws Exception {
        return get(type, host, port, user, passwd, null, timeout);
    }

    public static Sftp get(SftpType type, String host, int port, String user, String passwd, File knownHosts, Duration timeout) throws Exception {
        return get(type, host, port, user, passwd, null, knownHosts, timeout);
    }

    private static Sftp get(SftpType type, String host, int port, String user, String passwd, File publicKey, File knownHosts, Duration timeout) throws Exception {
        switch (type) {
            case JSCH:
                return new JschSftpImpl(host, port, user, passwd, timeout);

            case SSHJ:
                return new SshjSftpImpl(host, port, user, passwd, timeout);

            case SSHD:
                return new SshdSftpImpl(host, port, user, passwd, timeout);

            case VFS:
                return new VfsSftpImpl(host, port, user, passwd, publicKey, knownHosts, timeout);

            default:
                throw new NotImplementedException(type.name());
        }
    }

}
