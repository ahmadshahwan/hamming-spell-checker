package com.acme.cli;

import com.acme.hamming.CaseInsensitiveHammingMeter;
import com.acme.spellchecker.Dictionary;
import com.acme.spellchecker.SpellCheckResult;
import com.acme.spellchecker.SpellChecker;
import com.acme.spellchecker.Suggestion;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Checker {
    public static void main(String[] args) {
        SpellChecker checker = new SpellChecker(
                Dictionary.fromResource("english.txt"),
                new CaseInsensitiveHammingMeter()
        );
        try (Scanner scanner = new Scanner(System.in)) {
            String line = scanner.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                SpellCheckResult result = checker.check(word);
                if (!result.isCorrect()) {
                    String suggestions = result.suggestion().stream()
                            .limit(5)
                            .map(Suggestion::word)
                            .collect(Collectors.joining(", "));
                    System.out.printf("Misspell in word \"%s\"\n", word);
                    System.out.printf("\tSuggestions: %s\n", suggestions);
                }
            }
        }
    }
}