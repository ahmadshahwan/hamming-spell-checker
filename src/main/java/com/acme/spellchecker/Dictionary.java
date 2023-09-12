package com.acme.spellchecker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Dictionary {

    List<List<String>> words = new ArrayList<>();


    public void add(String word) {
        Objects.requireNonNull(word);
        List<String> sameLengthWords = this.getOrCreateWordsWithLength(word.length());
        sameLengthWords.add(word);
    }

    public List<String> getWordsWithLength(int length) {
        if (length >= this.words.size()) {
            return List.of();
        }
        return this.words.get(length);
    }

    private List<String> getOrCreateWordsWithLength(int length) {
        if (length >= this.words.size()) {
            for (int i = this.words.size(); i <= length; i++) {
                this.words.add(i, new ArrayList<>());
            }
        }
        return this.words.get(length);
    }

    public static Dictionary fromInputStream(InputStream is) {
        Dictionary dictionary = new Dictionary();
        if (is != null) {
            Scanner scanner = new Scanner(is);
            while (scanner.hasNext()) {
                dictionary.add(scanner.next());
            }
        }
        return dictionary;
    }

    public static Dictionary fromResource(String name) {
        try (InputStream is = Dictionary.class.getClassLoader().getResourceAsStream(name)) {
            return fromInputStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
