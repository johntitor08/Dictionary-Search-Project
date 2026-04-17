package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryLoader {
    public static class Dict {
        public final String[] words;
        public final String[] defs;

        public Dict(String[] w, String[] d) {
            words = w;
            defs = d;
        }
    }

    // FIX: single pass with ArrayList; proper try-with-resources so reader is never leaked
    public static Dict load(String path) throws IOException {
        List<String> words = new ArrayList<>();
        List<String> defs  = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String word = line;
                String def  = "";
                int c = findFirstCommaOutsideQuotes(line);
                if (c >= 0) {
                    word = line.substring(0, c).trim();
                    def  = line.substring(c + 1).trim();
                    if (def.length() >= 2 && def.charAt(0) == '"' && def.charAt(def.length() - 1) == '"') {
                        def = def.substring(1, def.length() - 1);
                    }
                }
                words.add(word);
                defs.add(def);
            }
        }

        return new Dict(words.toArray(new String[0]), defs.toArray(new String[0]));
    }

    private static int findFirstCommaOutsideQuotes(String s) {
        boolean inQuotes = false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '"')
                inQuotes = !inQuotes;
            else if (ch == ',' && !inQuotes)
                return i;
        }
        return -1;
    }
}
