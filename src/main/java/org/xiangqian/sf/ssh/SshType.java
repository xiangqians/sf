package org.xiangqian.sf.ssh;

/**
 * @author xiangqian
 * @date 19:40 2023/07/28
 */
public enum SshType {
    // JSch
    JSCH_CMD,
    JSCH_SHELL,

    // SSHJ
    SSHJ_CMD,
    SSHJ_SHELL,

    // SSHD
    SSHD_CMD,
    SSHD_SHELL,
}
