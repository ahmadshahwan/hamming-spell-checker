package com.acme.spellchecker;

import java.util.List;

public record SpellCheckResult(
        boolean isCorrect,
        List<Suggestion> suggestion
) {

    private static final SpellCheckResult CORRECT = new SpellCheckResult(true, List.of());

    public static SpellCheckResult correct() {
        return CORRECT;
    }

    public static SpellCheckResult fromSuggestions(List<Suggestion> suggestion) {
        return new SpellCheckResult(false, suggestion);
    }

}
