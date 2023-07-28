package org.xiangqian.sf.sftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.time.Duration;
import java.util.List;

/**
 * @author xiangqian
 * @date 21:11 2023/07/28
 */
@Slf4j
public class HelloWorld {

    public static void main(String[] args) throws Exception {
        Sftp sftp = null;
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

            // 获取sftp实例
            sftp = SftpFactory.get(SftpType.JSCH, host, port, user, passwd, timeout);

            // 查询当前目录下的文件列表
            List<FileEntry> list = sftp.ls("./", Duration.ofSeconds(10));
            list.forEach(System.out::println);

            // 进入 test 目录
            sftp.cd("test", Duration.ofSeconds(10));

            // 上传文件
            sftp.put("src.txt", "dst.txt", Duration.ofMinutes(1));

            // 下载文件
            sftp.get("src.txt", "dst.txt", Duration.ofMinutes(1));
        } finally {
            IOUtils.closeQuietly(sftp);
        }
    }

}
