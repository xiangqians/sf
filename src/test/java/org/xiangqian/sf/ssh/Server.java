package org.xiangqian.sf.ssh;

import lombok.Data;
import org.xiangqian.sf.util.Yaml;

import java.io.FileNotFoundException;
import java.time.Duration;

/**
 * @author xiangqian
 * @date 20:09 2023/07/27
 */
@Data
public class Server {

    private String host;
    private int port;
    private String user;
    private String passwd;
    private Duration timeout;

    public static Server get() throws FileNotFoundException {
        Yaml yaml = new Yaml("E:\\tmp\\test\\server.yml");
        Server server = new Server();
        server.setHost(yaml.getString("server1.host"));
        server.setPort(yaml.getInt("server1.port"));
        server.setUser(yaml.getString("server1.user"));
        server.setPasswd(yaml.getString("server1.passwd"));
        server.setTimeout(Duration.ofSeconds(10));
        return server;
    }

}

