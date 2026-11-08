package edu.sdu.daa;

import java.util.ArrayList;
import java.util.List;

public final class KmpSearch implements StringSearch {
    private long comparisons;

    public int[] lps(String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        int[] result = new int[pattern.length()];
        int length = 0, i = 1;
        while (i < pattern.length()) {
            comparisons++;
            if (pattern.charAt(i) == pattern.charAt(length)) {
                result[i++] = ++length;
            } else if (length > 0) {
                length = result[length - 1];
            } else {
                result[i++] = 0;
            }
        }
        return result;
    }

    @Override
    public List<Integer> search(String text, String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        comparisons = 0;
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) return matches;
        int[] prefix = lps(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            comparisons++;
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    matches.add(i - j);
                    j = prefix[j - 1];
                }
            } else if (j > 0) {
                j = prefix[j - 1];
            } else {
                i++;
            }
        }
        return matches;
    }

    @Override public long comparisons() { return comparisons; }
    @Override public String name() { return "KMP"; }
}
