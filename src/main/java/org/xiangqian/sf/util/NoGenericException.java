package org.xiangqian.sf.util;

/**
 * 没有泛型异常
 *
 * @author xiangqian
 * @date 16:20 2023/07/29
 */
public class NoGenericException extends Exception {

    public NoGenericException() {
    }

    public NoGenericException(String message) {
        super(message);
    }

}
