package editdistance;
public class TwoRowsEditDistance {
    private long computedCells;
    // State: previous and current store the edit distances for two consecutive rows of the DP table.
    // Transition: Calculate each cell using the left, upper, and diagonal values from these two rows.
    // Base case: Initialize the first row with distances from the empty string.
    public int calculate(String first, String second) {
        if (second.length() > first.length()) {
            String temp = first;
            first = second;
            second = temp;
        }
        int n = first.length();
        int m = second.length();
        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];
        computedCells = 0;
        for (int j = 0; j <= m; j++) {
            previous[j] = j;
            computedCells++;
        }
        for (int i = 1; i <= n; i++) {
            current[0] = i;
            computedCells++;
            for (int j = 1; j <= m; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    current[j] = previous[j - 1];
                } else {
                    int insertion = current[j - 1];
                    int deletion = previous[j];
                    int replacement = previous[j - 1];
                    current[j] = 1 + Math.min(insertion,
                            Math.min(deletion, replacement));
                }
                computedCells++;
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[m];
    }
    public long getComputedCells() {
        return computedCells;
    }
}
