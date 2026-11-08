package edu.sdu.daa;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {
    private final List<EditDistance> distances = List.of(new RecursiveDistance(), new TableDistance(), new TwoRowsDistance());
    private final NaiveSearch naive = new NaiveSearch();
    private final KmpSearch kmp = new KmpSearch();

    @Test void referenceDistances() {
        for (EditDistance method : distances) {
            assertEquals(3, method.distance("kitten", "sitting"));
            assertEquals(2, method.distance("flaw", "lawn"));
            assertEquals(5, method.distance("intention", "execution"));
            assertEquals(3, method.distance("", "abc"));
            assertEquals(3, method.distance("abc", ""));
            assertEquals(0, method.distance("", ""));
            assertEquals(0, method.distance("abc", "abc"));
        }
    }

    @Test void randomDistanceAgreement() {
        Random random = new Random(2516);
        for (int t = 0; t < 100; t++) {
            String a = randomWord(random, random.nextInt(6));
            String b = randomWord(random, random.nextInt(6));
            int expected = distances.get(0).distance(a, b);
            for (EditDistance method : distances) assertEquals(expected, method.distance(a, b));
        }
    }

    @Test void referenceLps() {
        assertArrayEquals(new int[]{0, 1, 2, 0, 1, 2, 3, 3}, kmp.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4}, kmp.lps("ABABCABAB"));
    }

    @Test void referenceSearches() {
        for (StringSearch method : List.<StringSearch>of(naive, kmp)) {
            assertEquals(List.of(10), method.search("ABABDABACDABABCABAB", "ABABCABAB"));
            assertEquals(List.of(0, 1, 2, 3), method.search("AAAAA", "AA"));
            assertEquals(List.of(0, 2, 4), method.search("ABABABA", "ABA"));
            assertEquals(List.of(), method.search("AB", "ABCD"));
            assertEquals(List.of(), method.search("", "A"));
            assertThrows(IllegalArgumentException.class, () -> method.search("AB", ""));
        }
        assertThrows(IllegalArgumentException.class, () -> kmp.lps(""));
    }

    @Test void randomSearchAgreement() {
        Random random = new Random(2516);
        for (int t = 0; t < 100; t++) {
            String text = randomWord(random, random.nextInt(30));
            String pattern = randomWord(random, 1 + random.nextInt(8));
            assertEquals(naive.search(text, pattern), kmp.search(text, pattern));
        }
    }

    private static String randomWord(Random random, int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) builder.append("abc".charAt(random.nextInt(3)));
        return builder.toString();
    }
}
