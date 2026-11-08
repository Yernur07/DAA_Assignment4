package stringsearch;
import java.util.ArrayList;
import java.util.List;
public class NaiveStringSearch {
    private long characterComparisons;
    public int[] search(String text, String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }
        characterComparisons = 0;
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return new int[0];
        }
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length()) {
                characterComparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
                j++;
            }
            if (j == pattern.length()) {
                matches.add(i);
            }
        }
        return matches.stream().mapToInt(Integer::intValue).toArray();
    }
    public long getCharacterComparisons() {
        return characterComparisons;
    }
}