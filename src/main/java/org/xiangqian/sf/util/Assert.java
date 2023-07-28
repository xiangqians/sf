package org.xiangqian.sf.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiangqian
 * @date 19:18 2023/07/26
 */
public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(String str, String message) {
        isTrue(StringUtils.isEmpty(str), message);
    }

    public static void notEmpty(String str, String message) {
        isTrue(StringUtils.isNotEmpty(str), message);
    }

}
