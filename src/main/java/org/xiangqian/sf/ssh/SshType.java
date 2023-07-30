package org.xiangqian.sf.ssh;

/**
 * @author xiangqian
 * @date 19:40 2023/07/28
 */
public enum SshType {
    // jsch
    JSCH_CMD,
    JSCH_SHELL,

    // sshj
    SSHJ_CMD,
    SSHJ_SHELL,

    // sshd
    SSHD_CMD,
    SSHD_SHELL,

    // commons vfs
    VFS_CMD,
    VFS_SHELL,
}
