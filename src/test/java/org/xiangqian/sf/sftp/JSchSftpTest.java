package org.xiangqian.sf.sftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.sftp.impl.jsch.JschSftpImpl;
import org.xiangqian.sf.ssh.Server;

import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 01:17 2022/07/24
 */
@Slf4j
public class JschSftpTest {

    public static void main(String[] args) throws Exception {
//        put();
        get();
    }

    public static void get() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp();
            sftp.cd("./tmp", Duration.ofSeconds(10));
            ls(sftp);

            sftp.get("./apache-skywalking-apm-bin.zip", "C:\\Users\\xiangqian\\Desktop\\tmp\\sf", Duration.ofSeconds(10));
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    public static void put() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp();
            sftp.cd("./tmp", Duration.ofSeconds(10));
            ls(sftp);

//            sftp.put("E:\\tmp\\TeamViewer.zip", "./", Duration.ofSeconds(10));
            sftp.put("C:\\Users\\xiangqian\\Desktop\\tmp\\apache-skywalking-apm-bin.zip", "./", Duration.ofSeconds(10));
            ls(sftp);
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

    @Test
    public void ls() throws Exception {
        Sftp sftp = null;
        try {
            sftp = getSftp();

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

    private static void ls(Sftp sftp) throws Exception {
        ls(sftp, "./");
    }

    private static void ls(Sftp sftp, String path) throws Exception {
        List<FileEntry> list = sftp.ls(path, Duration.ofSeconds(10));
        list.forEach(System.out::println);
        System.out.println();
    }

    private static Sftp getSftp() throws Exception {
        Server server = Server.get();
        return new JschSftpImpl(server.getHost(), server.getPort(), server.getUser(), server.getPasswd(), server.getTimeout());
    }

}
