package stringsearch;
import java.util.ArrayList;
import java.util.List;
public class KMPStringSearch {
    private long characterComparisons;
    public int[] search(String text, String pattern) {
        characterComparisons = 0;
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return new int[0];
        }
        int[] lps = buildLps(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            characterComparisons++;
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    matches.add(i - j);
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return matches.stream().mapToInt(Integer::intValue).toArray();
    }
    public int[] lps(String pattern) {
        characterComparisons = 0;
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }
        return buildLps(pattern);
    }
    private int[] buildLps(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;
        while (i < pattern.length()) {
            characterComparisons++;
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    public long getCharacterComparisons() {
        return characterComparisons;
    }
}
