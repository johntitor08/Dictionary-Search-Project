package com.example;

public class SearchTimer {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java SearchTimer <dictionary_csv> <out_prefix>");
            System.out.println("Example: java SearchTimer dictionary.csv results/output");
            return;
        }
        String dictPath = args[0];
        String out = args[1];

        DictionaryLoader.Dict dict = DictionaryLoader.load(dictPath);
        ExperimentRunner runner = new ExperimentRunner(dict.words, dict.defs);
        runner.runExperiment(30, 20, 5, 12345L, 1, out);
    }
}