package org.xiangqian.sf.sftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xiangqian.sf.sftp.impl.jsch.JSchSftpImpl;
import org.xiangqian.sf.ssh.Server;

import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 01:17 2022/07/24
 */
@Slf4j
public class JSchSftpTest {//implements Closeable {

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
        return new JSchSftpImpl(server.getHost(), server.getPort(), server.getUser(), server.getPasswd(), server.getTimeout());
    }

//    private Sftp sftp;
//
//    public static void main(String[] args) throws Exception {
//        new SftpTest().main();
//    }
//
//    public void main() throws Exception {
//        try {
//            init();
////            testLs();
////            testMkdir();
//            testPut();
////            testGet();
//
//        } finally {
//            IOUtils.closeQuietly(this);
//        }
//    }
//
//    private void testPut() throws Exception {
//        sftp.cd("test");
////        ll();
//        sftp.put("C:\\Users\\xiangqian\\Desktop\\tmp\\Screenshot_1.png",
//                "Screenshot_1.png",
//                DefaultSftpProgressMonitor.builder().build(),
//                FileTransferMode.OVERWRITE);
////        sftp.put("C:\\Users\\xiangqian\\Desktop\\tmp\\apache-skywalking-java-agent-8.9.0.tgz",
////                "apache-skywalking-java-agent-8.9.0.tgz",
////                DefaultSftpProgressMonitor.builder().build(),
////                FileTransferMode.OVERWRITE);
//        ll();
//    }
//
//    private void testGet() throws Exception {
//        sftp.cd("test");
//        sftp.get("Screenshot_1.png",
//                "C:\\Users\\xiangqian\\Desktop\\tmp\\download\\",
//                DefaultSftpProgressMonitor.builder().build(),
//                FileTransferMode.OVERWRITE);
//        ll();
//        sftp.rm("Screenshot_1.png");
//        sftp.rm("apache-skywalking-java-agent-8.9.0.tgz");
//    }
//
//    private void testMkdir() throws Exception {
////        sftp.mkdir("test1");
//        ll();
//        sftp.rmdir("test1");
//        ll();
//    }
//
//    private void testLs() throws Exception {
//        String command = null;
//        List<FileEntry> results = null;
//
//        command = "./";
//        results = sftp.ls(command);
//        SshTest.print(command, results);
//
//        command = "/";
//        results = sftp.ls(command);
//        SshTest.print(command, results);
//    }
//
//    private void ll() throws Exception {
//        String command = "./";
//        List<FileEntry> results = sftp.ls(command);
//        SshTest.print(command, results);
//    }
//
//    private void init() throws JSchException {
//        sftp = JSchSftpImpl.builder()
//                .connectionProperties(SshTest.getConnectionProperties())
//                .sessionConnectTimeout(Duration.ofSeconds(60))
//                .channelConnectTimeout(Duration.ofSeconds(60))
//                .build();
//    }
//
//    @Override
//    public void close() throws IOException {
//        IOUtils.closeQuietly(sftp);
//    }
}
