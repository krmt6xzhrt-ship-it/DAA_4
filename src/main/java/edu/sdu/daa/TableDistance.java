package edu.sdu.daa;

public final class TableDistance implements EditDistance {
    private long cells;

    // State: dp[i][j] is the distance between prefixes of lengths i and j.
    // Transition: min of deletion, insertion and substitution (cost 0 for equal chars).
    // Base: dp[i][0]=i and dp[0][j]=j.
    @Override
    public int distance(String a, String b) {
        cells = 0;
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                cells++;
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1,
                        Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost));
            }
        }
        return dp[n][m];
    }

    @Override public long operations() { return cells; }
    @Override public String name() { return "Table"; }
}
