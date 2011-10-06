package com.itang.uritemplate;

import com.itang.uritemplate.impl.UriTemplateImpl;
import org.junit.Test;

public class BenchmarkTest {
    private static final String THE_PATTERN = "/user/{category}/{name}";
    private static int BASE = 10000;
    private static long[] TIMES = {BASE * 100, BASE * 1000};
    private UriTemplate impl = new UriTemplateImpl(THE_PATTERN);

    @Test
    public void test() {
        for (int i = 0; i < TIMES.length; i++) {
            TimeWatch tw = new TimeWatch();
            tw.start();
            times(TIMES[i], new Runnable() {
                public void run() {
                    impl.getVariableNames();
                }
            });
            tw.watch("impl:" + TIMES[i]);
            tw.end();
            tw.print();
            System.out.println("---");
        }
    }

    public void times(long number, Runnable run) {
        for (long i = 0; i < number; i++) {
            run.run();
        }
    }
}
