package org.xiangqian.sf.ssh.impl.jsch;

import lombok.Getter;

/**
 * JSch通道类型
 *
 * @author xiangqian
 * @date 02:33 2022/07/24
 */
@Getter
public enum ChannelType {

    SESSION("session"),
    SHELL("shell"),
    EXEC("exec"),
    SFTP("sftp"),
    ;

    private final String value;

    ChannelType(String value) {
        this.value = value;
    }

}
