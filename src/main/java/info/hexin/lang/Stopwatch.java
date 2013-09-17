package info.hexin.lang;

public class Stopwatch {
    private long start;
    private long end;

    public static Stopwatch begin() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        return stopwatch;
    }

    public void reset() {
        start = System.nanoTime();
        end = 0;
    }

    private void start() {
        start = System.nanoTime();
    }

    public String getDuration() {
        if (end == 0) {
            this.stop();
        }
        long duration = end - start;
        String durationString = Long.toString(duration);
        int length = durationString.length();
        if (length <= 3) {
            return durationString;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(durationString.charAt(i));
            if ((length - i - 1) % 3 == 0) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void stop() {
        end = System.nanoTime();
    }
}
