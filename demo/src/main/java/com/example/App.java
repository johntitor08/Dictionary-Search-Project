package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {

    private static final String CSV_FILE = "dict.csv";
    private static BST dictionaryBST = new BST();

    public static void main(String[] args) {
        System.out.println("=== DICTIONARY SEARCH USING BST ===");
        loadCSV();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Search word");
            System.out.println("2. Count dictionary entries");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    System.out.print("Enter word: ");
                    String word = sc.nextLine().trim();
                    String meaning = dictionaryBST.search(word);

                    if (meaning != null) {
                        System.out.println("Meaning: " + meaning);
                    } else {
                        System.out.println("Not found.");
                    }
                    break;

                case 2:
                    System.out.println("Total words: " + dictionaryBST.count());
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid.");
            }
        }
    }

    private static void loadCSV() {
        System.out.println("Loading dictionary...");

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    dictionaryBST.insert(new DictionaryEntry(parts[0].trim(), parts[1].trim()));
                }
            }

            System.out.println("Loaded successfully.");

        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Enter valid number: ");
            }
        }
    }
}
