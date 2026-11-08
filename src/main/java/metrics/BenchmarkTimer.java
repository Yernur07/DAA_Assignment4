package metrics;
import java.util.Arrays;
public class BenchmarkTimer {
    private static final int WARMUP_RUNS = 1;
    private static final int MEASURED_RUNS = 5;
    public double medianTimeMs(Runnable task) {
        for (int i = 0; i < WARMUP_RUNS; i++) {
            task.run();
        }
        double[] times = new double[MEASURED_RUNS];
        for (int i = 0; i < MEASURED_RUNS; i++) {
            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();
            times[i] = (end - start) / 1_000_000.0;
        }
        Arrays.sort(times);
        return times[MEASURED_RUNS / 2];
    }
}