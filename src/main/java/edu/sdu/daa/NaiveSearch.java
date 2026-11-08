package edu.sdu.daa;

import java.util.ArrayList;
import java.util.List;

public final class NaiveSearch implements StringSearch {
    private long comparisons;

    @Override
    public List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        comparisons = 0;
        List<Integer> matches = new ArrayList<>();
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length()) {
                comparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) matches.add(i);
        }
        return matches;
    }

    @Override public long comparisons() { return comparisons; }
    @Override public String name() { return "Naive"; }
}
