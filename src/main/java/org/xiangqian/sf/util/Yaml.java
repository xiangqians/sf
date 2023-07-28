package org.xiangqian.sf.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * @author xiangqian
 * @date 20:32 2023/05/19
 */
public class Yaml {

    private Map<String, String> map;

    public Yaml(String name) throws FileNotFoundException {
        this(new File(name));
    }

    public Yaml(File file) throws FileNotFoundException {
        this(new FileInputStream(file), true);
    }

    public Yaml(InputStream in, boolean close) {
        try {
            org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
            this.map = yaml.loadAs(in, Map.class);
        } finally {
            if (close) {
                IOUtils.closeQuietly(in);
            }
        }
    }

    public String getString(String key) {
        return Optional.ofNullable(get(key))
                .map(Object::toString)
                .orElse(null);
    }

    public Integer getInt(String key) {
        return Optional.ofNullable(get(key))
                .map(Object::toString)
                .map(Integer::parseInt)
                .orElse(null);
    }

    public Object get(String key) {
        if (Objects.isNull(key)) {
            return null;
        }

        Queue<String> keyQueue = new LinkedList<>();
        Arrays.stream(key.split("\\.")).forEach(keyQueue::add);
        return get(map, keyQueue);
    }

    private static Object get(Object source, Queue<String> keyQueue) {
        if (keyQueue.isEmpty()) {
            return source;
        }

        // poll
        String key = keyQueue.poll();

        // key1.key2.key3
        if (source instanceof Map) {
            Map<String, String> map = (Map<String, String>) source;
            if (MapUtils.isEmpty(map)) {
                return null;
            }

            Object value = map.get(key);
            return get(value, keyQueue);
        }

        // key1.key2.key4[2]
        if (source instanceof Collection) {
            return null;
        }

        return null;
    }

}
