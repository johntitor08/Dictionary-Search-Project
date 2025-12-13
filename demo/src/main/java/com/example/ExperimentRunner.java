package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class ExperimentRunner {
    private final LinearSearch linear;
    private final BinarySearch binary;
    private final MyHashTable hashtable;

    public ExperimentRunner(String[] words, String[] defs) {
        this.linear = new LinearSearch(words, defs);
        this.binary = new BinarySearch(words, defs);
        this.hashtable = new MyHashTable(words, defs, 100003);
    }

    public void runExperiment(int numQueries, int numPresent, int numAbsent, long seed, int runs, String outCsvPrefix)
            throws IOException {
        String[] queries = QuerySelector.select(linear.getWords(), numPresent, numAbsent, seed);
        int Q = queries.length;
        String csvAll = outCsvPrefix + "_per_query_times.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvAll));
        writer.write("query,linear_ns,binary_ns,hash_ns\n");

        warmup(queries);

        long[] totalsLinear = new long[runs];
        long[] totalsBinary = new long[runs];
        long[] totalsHash = new long[runs];

        for (int r = 0; r < runs; r++) {
            long tL = 0L, tB = 0L, tH = 0L;
            for (int i = 0; i < Q; i++) {
                String q = queries[i];
                long s, e;
                s = System.nanoTime();
                linear.search(q);
                e = System.nanoTime();
                tL += (e - s);
                s = System.nanoTime();
                binary.search(q);
                e = System.nanoTime();
                tB += (e - s);
                s = System.nanoTime();
                hashtable.get(q);
                e = System.nanoTime();
                tH += (e - s);

                if (r == runs - 1) {
                    writer.write(escapeCsv(q) + "," + (e - s) + "," + "0,0");
                }
            }
            totalsLinear[r] = tL;
            totalsBinary[r] = tB;
            totalsHash[r] = tH;
        }
        writer.close();

        printSummary("Linear", totalsLinear, Q);
        printSummary("Binary", totalsBinary, Q);
        printSummary("Hash", totalsHash, Q);

        System.out.println("Per-query CSV written to: " + csvAll);
    }

    private void warmup(String[] queries) {
        for (int k = 0; k < 3; k++)
            for (String q : queries) {
                linear.search(q);
                binary.search(q);
                hashtable.get(q);
            }
    }

    private void printSummary(String name, long[] totals, int Q) {
        double mean = mean(totals);
        double sd = stddev(totals, mean);
        System.out.printf(Locale.US, "%s: runs=%d mean_total_ms=%.3f mean_per_query_us=%.3f sd_ms=%.3f", name,
                totals.length, mean / 1_000_000.0, (mean / Q) / 1_000.0, sd / 1_000_000.0);
    }

    private double mean(long[] a) {
        double s = 0;
        for (long v : a)
            s += v;
        return s / a.length;
    }

    private double stddev(long[] a, double mean) {
        double s = 0;
        for (long v : a) {
            double d = v - mean;
            s += d * d;
        }
        return Math.sqrt(s / a.length);
    }

    private static String escapeCsv(String s) {
        if (s.indexOf(',') >= 0 || s.indexOf('"') >= 0)
            return '"' + s.replace("\"", "\"\"") + '"';
        return s;
    }
}