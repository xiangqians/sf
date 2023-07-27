package org.xiangqian.sf.util;

/**
 * Cleaner
 *
 * @author xiangqian
 * @date 19:56 2023/07/27
 */
public class Cleaner implements AutoCloseable { // 实现AutoCloseable接口

    // 创建一个回收对象
    private static final java.lang.ref.Cleaner cleaner = java.lang.ref.Cleaner.create();

    private java.lang.ref.Cleaner.Cleanable cleanable;

    public Cleaner(Runnable runnable) {
        // 注册一个回收线程
        cleanable = cleaner.register(this, runnable);
    }

    @Override
    public void close() throws Exception {
        // 释放时进行清除
        cleanable.clean();
    }

}
