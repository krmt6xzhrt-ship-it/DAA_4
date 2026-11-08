package edu.sdu.daa;

public final class RecursiveDistance implements EditDistance {
    private long calls;

    // State: distance(i,j) is the cost for the first i and j characters.
    // Transition: equal last letters -> (i-1,j-1); otherwise 1 + min(delete,insert,replace).
    // Base: distance(0,j)=j and distance(i,0)=i.
    @Override
    public int distance(String a, String b) {
        calls = 0;
        return solve(a, b, a.length(), b.length());
    }

    private int solve(String a, String b, int i, int j) {
        calls++;
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) return solve(a, b, i - 1, j - 1);
        int delete = solve(a, b, i - 1, j);
        int insert = solve(a, b, i, j - 1);
        int replace = solve(a, b, i - 1, j - 1);
        return 1 + Math.min(replace, Math.min(delete, insert));
    }

    @Override public long operations() { return calls; }
    @Override public String name() { return "Recursive"; }
}
