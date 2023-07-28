package org.xiangqian.sf;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;

import java.time.Duration;
import java.time.Instant;

/**
 * @author xiangqian
 * @date 20:17 2023/07/28
 */
@Slf4j
public abstract class AbsTest {

    private Instant start;

    @Before
    public final void before() {
        start = Instant.now();
    }

    @After
    public final void after() {
        Instant end = Instant.now();
        long ms = Duration.between(start, end).toMillis();
        System.out.println();
        log.debug("耗时：{}", humanizMs(ms));
    }

    /**
     * 人性化毫秒
     *
     * @param ms
     * @return
     */
    private String humanizMs(long ms) {
        long m = ms / 1000 / 60;
        if (m > 0) {
            String text = m + " m ";
            long remainder = ms / 1000 % 60;
            if (remainder != 0) {
                text += remainder + " s ";
            }
            return text;
        }

        long s = ms / 1000;
        if (s > 0) {
            String text = s + " s ";
            long remainder = ms % 1000;
            if (remainder != 0) {
                text += remainder + " ms ";
            }
            return text;
        }

        return ms + " ms ";
    }

}
