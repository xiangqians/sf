package org.xiangqian.sf.sftp.impl.jsch;

import com.jcraft.jsch.SftpProgressMonitor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * public static void main(String[] args){}
 * 只能在main方法使用才会显示进度条，@Test运行无法显示
 *
 * @author xiangqian
 * @date 00:38 2022/07/26
 */
public class DefaultSftpProgressMonitor implements SftpProgressMonitor {

    private static final Map<Integer, String> OP_MAP = Map.of(PUT, "PUT", GET, "GET");
    private long totalSize;
    private long transferredSize;

    public DefaultSftpProgressMonitor() {
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        System.out.println("Start transferring files ...");
        totalSize = max;
        System.out.format("%s %s (%s) -> %s",
                OP_MAP.get(op),
                src,
                humanizFileSize(totalSize),
                dest).println();
    }

    @Override
    public boolean count(long count) {
        transferredSize += count;
        System.out.print('\r');
        String temp = String.format("%s have currently been transferred. (%s in total)",
                humanizFileSize(transferredSize),
                humanizFileSize(totalSize));
        System.out.print(temp);
        if (transferredSize >= totalSize) {
            System.out.println();
        }
        return true;
    }

    @Override
    public void end() {
        System.out.println("File transfer ended.\n");
    }

    /**
     * 人性化文件大小
     *
     * @param size 文件大小，单位：byte
     * @return
     */
    private static String humanizFileSize(long size) {
        // 1B  = 8b
        // 1KB = 1024B
        // 1MB = 1024KB
        // 1GB = 1024MB
        // 1TB = 1024GB

        if (size <= 0) {
            return "0 B";
        }

        // MB
        BigDecimal mb = new BigDecimal(String.valueOf(size)).divide(new BigDecimal(String.valueOf(1024 * 1024)), 2, RoundingMode.DOWN);
        if (mb.floatValue() > 1) {
            return String.format("%.2f MB", mb.floatValue());
        }

        // KB
        BigDecimal kb = new BigDecimal(String.valueOf(size)).divide(new BigDecimal(String.valueOf(1024)), 2, RoundingMode.DOWN);
        if (kb.floatValue() > 1) {
            return String.format("%.2f KB", kb.floatValue());
        }

        // B
        return String.format("%s B", size);
    }

}
