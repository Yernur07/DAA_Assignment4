package editdistance;
public class RecursiveEditDistance {
    private long recursiveCalls;
    // State: calculateRecursive(i, j) represents the minimum edit distance between the first i and j characters.
    // Transition: If characters differ, take 1 + min(insertion, deletion, replacement).
    // Base case: If either string is empty, the distance is the length of the other string.
    public int calculate(String first, String second) {
        recursiveCalls = 0;
        return calculateRecursive(first, second, first.length(), second.length());
    }
    private int calculateRecursive(String first, String second, int i, int j) {
        recursiveCalls++;
        if (i == 0) {
            return j;
        }
        if (j == 0) {
            return i;
        }
        if (first.charAt(i - 1) == second.charAt(j - 1)) {
            return calculateRecursive(first, second, i - 1, j - 1);
        }
        int insertion = calculateRecursive(first, second, i, j - 1);
        int deletion = calculateRecursive(first, second, i - 1, j);
        int replacement = calculateRecursive(first, second, i - 1, j - 1);
        return 1 + Math.min(insertion,
                Math.min(deletion, replacement));
    }
    public long getRecursiveCalls() {
        return recursiveCalls;
    }
}
