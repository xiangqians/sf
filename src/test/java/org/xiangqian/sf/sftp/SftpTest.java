package org.xiangqian.sf.sftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.AbsTest;
import org.xiangqian.sf.ssh.Server;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 01:17 2022/07/24
 */
@Slf4j
public class SftpTest extends AbsTest {

    public static void get() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp(SftpType.SSHJ);
            sftp.cd("./tmp", Duration.ofSeconds(10));
            ls(sftp);

            sftp.get("./apache-skywalking-apm-bin.zip", "C:\\Users\\xiangqian\\Desktop\\tmp\\sf", Duration.ofSeconds(10));
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    @Test
    public void put() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp(SftpType.SSHJ);
//            sftp.cd("./tmp", Duration.ofSeconds(10));
//            ls(sftp);

//            sftp.put("E:\\tmp\\TeamViewer.zip", "./", Duration.ofSeconds(10));
            sftp.put("C:\\Users\\xiangqian\\Desktop\\tmp\\apache-skywalking-apm-bin.zip", "./", Duration.ofSeconds(10));
            ls(sftp);
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    @Test
    public void putVfs() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp(SftpType.VFS);
            String dst = "/home/xiangqian/tmp";
            ls(sftp, dst);

            sftp.put("C:\\Users\\xiangqian\\Desktop\\tmp\\apache-maven-3.0.5-bin.tar.gz", "/home/xiangqian/tmp/test", Duration.ofSeconds(10));
            ls(sftp, dst);
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    @Test
    public void ls1() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp(SftpType.SSHJ);

            // ls
            ls(sftp);

            // cd
            sftp.cd("./tmp", Duration.ofSeconds(10));
            ls(sftp);

            // mkdir
//            sftp.mkdir("test", Duration.ofSeconds(10));
//            ls(sftp);

            // rm
//            sftp.rm("test", Duration.ofSeconds(10));
            sftp.rm("./test", Duration.ofSeconds(10));
            ls(sftp);

        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    @Test
    public void ls() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp(SftpType.VFS);
            ls(sftp, "/home/xiangqian");
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    private static void ls(Sftp sftp) throws Exception {
        ls(sftp, "./");
    }

    private static void ls(Sftp sftp, String path) throws Exception {
        List<FileEntry> list = sftp.ls(path, Duration.ofSeconds(10));
        list.forEach(System.out::println);
        System.out.println();
    }

    public static Sftp getSftp(SftpType type) throws Exception {
        Server server = Server.get();
        String host = server.getHost();
        int port = server.getPort();
        String user = server.getUser();
        String passwd = server.getPasswd();
        Duration timeout = server.getTimeout();

        Sftp sftp = null;
        if (type == SftpType.VFS) {
            File knownHosts = new File("E:\\cygwin64\\home\\xiangqian\\.ssh\\known_hosts");
            sftp = SftpFactory.get(type, host, port, user, passwd, knownHosts, timeout);
        } else {
            sftp = SftpFactory.get(type, host, port, user, passwd, timeout);
        }

        log.debug("sftp impl: {}", sftp.getClass());
        return sftp;
    }

}
