package com.acme.spellchecker;

import com.acme.hamming.HammingMeter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SpellChecker {

    private static final int DEFAULT_DISTANCE_MAX = 3;

    final protected Dictionary dictionary;
    final protected HammingMeter hammingMeter;
    final protected int distanceMax;


    public SpellChecker(
            Dictionary dictionary,
            HammingMeter hammingMeter,
            int distanceMax
    ) {
        this.dictionary = dictionary;
        this.hammingMeter = hammingMeter;
        this.distanceMax = distanceMax;
    }

    public SpellChecker(
            Dictionary dictionary,
            HammingMeter hammingMeter
    ) {
        this(dictionary, hammingMeter, DEFAULT_DISTANCE_MAX);
    }

    public SpellCheckResult check(String word, int distanceMax) {
        Objects.requireNonNull(word);
        List<String> sameLengthWords = this.dictionary.getWordsWithLength(word.length());
        List<Suggestion> suggestions = new ArrayList<>();
        int limit = Math.min(word.length() / 2, distanceMax);
        for (String other: sameLengthWords) {
            int distance = this.hammingMeter.distance(word, other);
            if (distance == 0) {
               return SpellCheckResult.correct();
            }
            if (distance <= limit) {
                suggestions.add(new Suggestion(other, distance));
            }
        }
        suggestions.sort(Comparator.comparingInt(Suggestion::distance));
        return SpellCheckResult.fromSuggestions(suggestions);
    }

    public SpellCheckResult check(String word) {
        return this.check(word, this.distanceMax);
    }
}
