package org.xiangqian.sf.ssh.impl.sshd;

import java.io.Closeable;
import java.io.IOException;

/**
 * Apache MINA SSHD
 * https://mina.apache.org/sshd-project/documentation.html
 * https://github.com/apache/mina-sshd
 *
 * @author xiangqian
 * @date 15:49 2023/07/29
 */
public abstract class SshdSupport implements Closeable {

    @Override
    public void close() throws IOException {

    }

}
