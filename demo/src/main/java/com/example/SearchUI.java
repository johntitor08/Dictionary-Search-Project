package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SearchUI {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java SearchUI <dictionary_csv>");
            return;
        }
        DictionaryLoader.Dict dict = DictionaryLoader.load(args[0]);
        LinearSearch ls = new LinearSearch(dict.words, dict.defs);
        BinarySearch bs = new BinarySearch(dict.words, dict.defs);
        MyHashTable ht = new MyHashTable(dict.words, dict.defs, 100003);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Simple Dictionary Search UI (console)");
        System.out.println("Commands: alg [linear|binary|hash], search <word>, exit");
        String alg = "hash";
        while (true) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null)
                break;
            line = line.trim();
            if (line.isEmpty())
                continue;
            if (line.equalsIgnoreCase("exit"))
                break;
            if (line.startsWith("alg ")) {
                alg = line.substring(4).trim();
                System.out.println("Algorithm set to: " + alg);
                continue;
            }
            if (line.startsWith("search ")) {
                String key = line.substring(7).trim();
                long s = System.nanoTime();
                String def = null;
                if (alg.equalsIgnoreCase("linear"))
                    def = ls.search(key);
                else if (alg.equalsIgnoreCase("binary"))
                    def = bs.search(key);
                else
                    def = ht.get(key);
                long e = System.nanoTime();
                double ms = (e - s) / 1_000_000.0;
                if (def == null)
                    System.out.printf("Not found. Time: %.4f ms\n", ms);
                else
                    System.out.printf("Found. Time: %.4f ms\nDefinition: %s\n", ms, def);
                continue;
            }
            System.out.println("Unknown command.");
        }
        System.out.println("Bye.");
    }
}