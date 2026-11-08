package edu.sdu.daa;

public final class TwoRowsDistance implements EditDistance {
    private long cells;

    // State: previous[j] and current[j] hold DP values for the last two rows.
    // Transition: min(delete, insert, substitute), using previous and current values.
    // Base: first row is 0..m; each new row begins at its prefix length i.
    @Override
    public int distance(String a, String b) {
        cells = 0;
        if (b.length() > a.length()) {
            String temp = a;
            a = b;
            b = temp;
        }
        int n = a.length(), m = b.length();
        int[] previous = new int[m + 1], current = new int[m + 1];
        for (int j = 0; j <= m; j++) previous[j] = j;
        for (int i = 1; i <= n; i++) {
            current[0] = i;
            for (int j = 1; j <= m; j++) {
                cells++;
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                current[j] = Math.min(previous[j] + 1,
                        Math.min(current[j - 1] + 1, previous[j - 1] + cost));
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[m];
    }

    @Override public long operations() { return cells; }
    @Override public String name() { return "TwoRows"; }
}
