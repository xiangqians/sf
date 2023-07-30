# SF

ssh, sftp 工具

依赖库：

- [JSch](https://github.com/is/jsch)
- [SSHJ](https://github.com/hierynomus/sshj)
- [SSHD](https://github.com/apache/mina-sshd)
- [Commons VFS]()

# 示例

## SSH

```java
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.xiangqian.sf.ssh.SshType;
import org.xiangqian.sf.ssh.Ssh;
import org.xiangqian.sf.ssh.SshFactory;

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
```

## SFTP

```java
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.xiangqian.sf.sftp.Sftp;
import org.xiangqian.sf.sftp.SftpType;
import org.xiangqian.sf.sftp.SftpFactory;
import org.xiangqian.sf.sftp.FileEntry;

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
```
