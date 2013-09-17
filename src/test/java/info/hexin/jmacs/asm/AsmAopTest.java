package info.hexin.jmacs.asm;

import org.junit.Test;

public class AsmAopTest {
    @Test
    public void testAop() {

    }

    public static class Foo {
        public static void execute() {
            System.out.println("test changed method name");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Monitor {

        static long start = 0;

        public static void start() {
            start = System.currentTimeMillis();
        }

        public static void end() {
            long end = System.currentTimeMillis();
            System.out.println("execute method use time :" + (end - start));
        }
    }
}
