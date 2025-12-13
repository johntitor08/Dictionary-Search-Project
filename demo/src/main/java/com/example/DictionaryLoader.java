package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryLoader {
    public static class Dict {
        public final String[] words;
        public final String[] defs;

        public Dict(String[] w, String[] d) {
            words = w;
            defs = d;
        }
    }

    public static Dict load(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty())
                count++;
        }
        br.close();

        String[] words = new String[count];
        String[] defs = new String[count];

        br = new BufferedReader(new FileReader(path));
        int idx = 0;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;
            String word = line;
            String def = "";
            int c = findFirstCommaOutsideQuotes(line);
            if (c >= 0) {
                word = line.substring(0, c).trim();
                def = line.substring(c + 1).trim();
                // remove surrounding quotes if present
                if (def.length() >= 2 && def.charAt(0) == '"' && def.charAt(def.length() - 1) == '"') {
                    def = def.substring(1, def.length() - 1);
                }
            }
            words[idx] = word;
            defs[idx] = def;
            idx++;
        }
        br.close();
        return new Dict(words, defs);
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