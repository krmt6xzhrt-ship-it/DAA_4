package edu.sdu.daa;

import java.util.Random;

public final class InputGenerator {
    private InputGenerator() {}
    public static String random(int n, long seed, String alphabet) {
        Random random = new Random(seed);
        StringBuilder builder = new StringBuilder(n);
        for (int i = 0; i < n; i++) builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return builder.toString();
    }
    public static String repeat(char ch, int n) {
        StringBuilder builder = new StringBuilder(n);
        for (int i = 0; i < n; i++) builder.append(ch);
        return builder.toString();
    }
}
