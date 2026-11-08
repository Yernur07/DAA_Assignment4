package stringsearch;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
class StringSearchTest {
    private final NaiveStringSearch naive = new NaiveStringSearch();
    private final KMPStringSearch kmp = new KMPStringSearch();
    @Test
    void shouldFindSingleMatch() {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        assertArrayEquals(new int[]{10}, naive.search(text, pattern));
        assertArrayEquals(new int[]{10}, kmp.search(text, pattern));
    }

    @Test
    void shouldFindOverlappingMatches() {
        assertArrayEquals(new int[]{0, 1, 2, 3},
                naive.search("AAAAA", "AA"));
        assertArrayEquals(new int[]{0, 1, 2, 3},
                kmp.search("AAAAA", "AA"));
        assertArrayEquals(new int[]{0, 2, 4},
                naive.search("ABABABA", "ABA"));
        assertArrayEquals(new int[]{0, 2, 4},
                kmp.search("ABABABA", "ABA"));
    }

    @Test
    void shouldCalculateLPS() {
        assertArrayEquals(
                new int[]{0, 1, 2, 0, 1, 2, 3, 3},
                kmp.lps("AAACAAAA")
        );
        assertArrayEquals(
                new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4},
                kmp.lps("ABABCABAB")
        );
    }

    @Test
    void shouldHandlePatternLongerThanText() {
        assertArrayEquals(new int[0], naive.search("ABC", "ABCDEFG"));
        assertArrayEquals(new int[0], kmp.search("ABC", "ABCDEFG"));
    }

    @Test
    void shouldRejectEmptyPattern() {
        assertThrows(IllegalArgumentException.class,
                () -> naive.search("ABC", ""));
        assertThrows(IllegalArgumentException.class,
                () -> kmp.search("ABC", ""));
    }

    @Test
    void shouldMatchRandomSearchResults() {
        Random random = new Random(42);
        String alphabet = "ABC";
        for (int test = 0; test < 100; test++) {
            String text = randomString(random, alphabet, 20);
            String pattern = randomString(random, alphabet, 8);
            if (pattern.isEmpty()) {
                pattern = "A";
            }
            assertArrayEquals(
                    naive.search(text, pattern),
                    kmp.search(text, pattern)
            );
        }
    }

    @Test
    void shouldCountCharacterComparisons() {
        naive.search("AAAAAB", "AAB");
        kmp.search("AAAAAB", "AAB");
        assertTrue(naive.getCharacterComparisons() > 0);
        assertTrue(kmp.getCharacterComparisons() > 0);
    }

    private String randomString(Random random, String alphabet, int maxLength) {
        int length = random.nextInt(maxLength + 1);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }
}
