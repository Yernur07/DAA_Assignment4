package editdistance;
public class TableEditDistance {
    private long computedCells;
    // State: dp[i][j] stores the minimum edit distance between the first i and j characters.
    // Transition: Use the minimum cost of insertion, deletion, and replacement, or copy the diagonal value if characters match.
    // Base case: The first row and column contain distances from or to an empty string.
    public int calculate(String first, String second) {
        int n = first.length();
        int m = second.length();
        int[][] dp = new int[n + 1][m + 1];
        computedCells = 0;
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i;
            computedCells++;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
            computedCells++;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insertion = dp[i][j - 1];
                    int deletion = dp[i - 1][j];
                    int replacement = dp[i - 1][j - 1];
                    dp[i][j] = 1 + Math.min(insertion,
                            Math.min(deletion, replacement));
                }
                computedCells++;
            }
        }
        return dp[n][m];
    }
    public long getComputedCells() {
        return computedCells;
    }
}
