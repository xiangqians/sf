package org.xiangqian.sf.ssh;

import java.io.Closeable;
import java.io.InputStream;
import java.time.Duration;

/**
 * ssh
 *
 * @author xiangqian
 * @date 13:10 2022/07/23
 */
public interface Ssh extends Closeable {

    /**
     * 执行命令
     *
     * @param cmd     命令
     * @param timeout 执行命令超时时间
     * @return 执行结果stream
     * @throws Exception
     */
    InputStream exec(String cmd, Duration timeout) throws Exception;

}
