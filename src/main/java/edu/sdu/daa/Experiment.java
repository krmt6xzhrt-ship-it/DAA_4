package edu.sdu.daa;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public final class Experiment {
    private static final long SEED = 2516L;
    private static final int RUNS = 5;
    private static volatile int checksum;

    public static void main(String[] args) throws IOException {
        Path path = Path.of("results", "results.csv");
        Files.createDirectories(path.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("algorithm,input,n,pattern_length,run,time_ms,operations,answer\n");
            editExperiments(writer);
            searchExperiments(writer);
        }
        System.out.println("Saved " + path + ", checksum=" + checksum);
    }

    private static void editExperiments(BufferedWriter writer) throws IOException {
        List<EditDistance> methods = List.of(new RecursiveDistance(), new TableDistance(), new TwoRowsDistance());
        for (EditDistance method : methods) {
            int[] sizes = method instanceof RecursiveDistance ? new int[]{3, 6, 9, 12} : new int[]{64, 128, 256, 512};
            for (String kind : List.of("random", "disjoint")) {
                for (int n : sizes) {
                    String a = kind.equals("random") ? InputGenerator.random(n, SEED + n, "abcd") : InputGenerator.repeat('a', n);
                    String b = kind.equals("random") ? InputGenerator.random(n, SEED + n + 1, "abcd") : InputGenerator.repeat('b', n);
                    for (int run = 1; run <= RUNS; run++) {
                        long start = System.nanoTime();
                        int answer = method.distance(a, b);
                        long elapsed = System.nanoTime() - start;
                        checksum ^= answer;
                        save(writer, method.name(), kind, n, 0, run, elapsed, method.operations(), answer);
                    }
                }
            }
        }
    }

    private static void searchExperiments(BufferedWriter writer) throws IOException {
        List<StringSearch> methods = List.of(new NaiveSearch(), new KmpSearch());
        for (StringSearch method : methods) {
            for (String kind : List.of("random", "repetitive")) {
                for (int n : new int[]{2000, 8000, 32000, 128000}) {
                    int m = 16;
                    String text = kind.equals("random") ? InputGenerator.random(n, SEED + n, "abcd") : InputGenerator.repeat('a', n);
                    String pattern = kind.equals("random") ? text.substring(n / 2, n / 2 + m) : InputGenerator.repeat('a', m - 1) + "b";
                    for (int run = 1; run <= RUNS; run++) {
                        long start = System.nanoTime();
                        List<Integer> answer = method.search(text, pattern);
                        long elapsed = System.nanoTime() - start;
                        checksum ^= answer.size();
                        save(writer, method.name(), kind, n, m, run, elapsed, method.comparisons(), answer.size());
                    }
                }
            }
        }
    }

    private static void save(BufferedWriter writer, String algorithm, String kind, int n, int m,
                             int run, long nanos, long operations, int answer) throws IOException {
        writer.write(String.format(Locale.US, "%s,%s,%d,%d,%d,%.6f,%d,%d%n",
                algorithm, kind, n, m, run, nanos / 1_000_000.0, operations, answer));
    }
}
