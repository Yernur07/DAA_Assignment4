package benchmark;
import editdistance.RecursiveEditDistance;
import editdistance.TableEditDistance;
import editdistance.TwoRowsEditDistance;
import metrics.BenchmarkTimer;
import stringsearch.KMPStringSearch;
import stringsearch.NaiveStringSearch;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class BenchmarkMain {
    private static final int[] RECURSIVE_SIZES = {2, 3, 4, 8};
    private static final int[] DP_SIZES = {100, 200, 400, 800};
    private static final int[] SEARCH_SIZES = {10000, 20000, 40000, 80000};
    private final RandomDataGenerator generator = new RandomDataGenerator();
    private final BenchmarkTimer timer = new BenchmarkTimer();
    public static void main(String[] args) {
        new BenchmarkMain().run();
    }
    public void run() {
        File directory = new File("results");
        directory.mkdirs();
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("results/results.csv"))) {
            writer.println("algorithm,input_type,n,time_ms,operations");
            benchmarkRecursive(writer);
            benchmarkTable(writer);
            benchmarkTwoRows(writer);
            benchmarkNaive(writer);
            benchmarkKMP(writer);
            System.out.println("Benchmark completed.");
            System.out.println("Results saved to results/results.csv");
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }
    private void benchmarkRecursive(PrintWriter writer) {
        RecursiveEditDistance algorithm = new RecursiveEditDistance();
        for (int n : RECURSIVE_SIZES) {
            String randomFirst = generator.randomString(n);
            String randomSecond = generator.randomString(n);
            measureEditDistance(writer, algorithm, "Recursive",
                    "random", n, randomFirst, randomSecond);
            String first = generator.repeatedString(n, 'A');
            String second = generator.repeatedString(n, 'B');
            measureEditDistance(writer, algorithm, "Recursive",
                    "no_common", n, first, second);
        }
    }
    private void benchmarkTable(PrintWriter writer) {
        TableEditDistance algorithm = new TableEditDistance();
        for (int n : DP_SIZES) {
            String first = generator.randomString(n);
            String second = generator.randomString(n);
            measureEditDistance(writer, algorithm, "Table",
                    "random", n, first, second);
            String noCommonFirst = generator.repeatedString(n, 'A');
            String noCommonSecond = generator.repeatedString(n, 'B');
            measureEditDistance(writer, algorithm, "Table",
                    "no_common", n, noCommonFirst, noCommonSecond);
        }
    }
    private void benchmarkTwoRows(PrintWriter writer) {
        TwoRowsEditDistance algorithm = new TwoRowsEditDistance();
        for (int n : DP_SIZES) {
            String first = generator.randomString(n);
            String second = generator.randomString(n);
            measureEditDistance(writer, algorithm, "TwoRows",
                    "random", n, first, second);
            String noCommonFirst = generator.repeatedString(n, 'A');
            String noCommonSecond = generator.repeatedString(n, 'B');
            measureEditDistance(writer, algorithm, "TwoRows",
                    "no_common", n, noCommonFirst, noCommonSecond);
        }
    }
    private void measureEditDistance(PrintWriter writer,
                                     RecursiveEditDistance algorithm,
                                     String name, String type, int n,
                                     String first, String second) {
        double time = timer.medianTimeMs(
                () -> algorithm.calculate(first, second)
        );
        writeRow(writer, name, type, n,
                time, algorithm.getRecursiveCalls());
    }
    private void measureEditDistance(PrintWriter writer,
                                     TableEditDistance algorithm,
                                     String name, String type, int n,
                                     String first, String second) {
        double time = timer.medianTimeMs(
                () -> algorithm.calculate(first, second)
        );
        writeRow(writer, name, type, n,
                time, algorithm.getComputedCells());
    }
    private void measureEditDistance(PrintWriter writer,
                                     TwoRowsEditDistance algorithm,
                                     String name, String type, int n,
                                     String first, String second) {
        double time = timer.medianTimeMs(
                () -> algorithm.calculate(first, second)
        );
        writeRow(writer, name, type, n,
                time, algorithm.getComputedCells());
    }
    private void benchmarkNaive(PrintWriter writer) {
        NaiveStringSearch algorithm = new NaiveStringSearch();
        for (int n : SEARCH_SIZES) {
            String text = generator.randomString(n);
            String pattern = generator.randomString(8);
            double time = timer.medianTimeMs(
                    () -> algorithm.search(text, pattern)
            );
            writeRow(writer, "Naive", "random", n,
                    time, algorithm.getCharacterComparisons());
            String worstText = generator.repeatedString(n, 'A');
            String worstPattern = "AAAAB";
            time = timer.medianTimeMs(
                    () -> algorithm.search(worstText, worstPattern)
            );
            writeRow(writer, "Naive", "worst_case", n,
                    time, algorithm.getCharacterComparisons());
        }
    }
    private void benchmarkKMP(PrintWriter writer) {
        KMPStringSearch algorithm = new KMPStringSearch();
        for (int n : SEARCH_SIZES) {
            String text = generator.randomString(n);
            String pattern = generator.randomString(8);
            double time = timer.medianTimeMs(
                    () -> algorithm.search(text, pattern)
            );
            writeRow(writer, "KMP", "random", n,
                    time, algorithm.getCharacterComparisons());
            String worstText = generator.repeatedString(n, 'A');
            String worstPattern = "AAAAB";
            time = timer.medianTimeMs(
                    () -> algorithm.search(worstText, worstPattern)
            );
            writeRow(writer, "KMP", "worst_case", n,
                    time, algorithm.getCharacterComparisons());
        }
    }
    private void writeRow(PrintWriter writer, String algorithm,
                          String inputType, int n,
                          double time, long operations) {
        writer.printf(java.util.Locale.US,
                "%s,%s,%d,%.6f,%d%n",
                algorithm, inputType, n, time, operations);
    }
}