package com.acme.spellchecker;

public record Suggestion(
        String word,
        int distance
) {}
