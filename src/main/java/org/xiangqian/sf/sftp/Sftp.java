package org.xiangqian.sf.sftp;

import java.io.Closeable;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;

/**
 * SFTP
 *
 * @author xiangqian
 * @date 00:04 2022/07/24
 */
public interface Sftp extends Closeable {

    /**
     * 查询指定目录下的文件列表
     *
     * @param path
     * @param timeout 执行命令超时时间
     * @return
     * @throws Exception
     */
    List<FileEntry> ls(String path, Duration timeout) throws Exception;

    /**
     * 进入指定目录
     *
     * @param path
     * @param timeout 执行命令超时时间
     * @throws Exception
     */
    void cd(String path, Duration timeout) throws Exception;

    /**
     * 创建目录
     *
     * @param path
     * @param timeout
     * @throws Exception
     */
    void mkdir(String path, Duration timeout) throws Exception;

    /**
     * 删除文件
     *
     * @param path
     * @param timeout 执行命令超时时间
     * @throws Exception
     */
    void rm(String path, Duration timeout) throws Exception;

    /**
     * 上传文件
     *
     * @param src     本地源文件
     * @param dst     远程服务器源文件
     * @param timeout 执行命令超时时间
     * @return
     * @throws Exception
     */
    InputStream put(String src, String dst, Duration timeout) throws Exception;

    /**
     * 下载文件
     *
     * @param src     远程服务器源文件
     * @param dst     本地目标文件
     * @param timeout 执行命令超时时间
     * @return
     * @throws Exception
     */
    InputStream get(String src, String dst, Duration timeout) throws Exception;

}
