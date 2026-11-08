package editdistance;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
class EditDistanceTest {
    private final RecursiveEditDistance recursive = new RecursiveEditDistance();
    private final TableEditDistance table = new TableEditDistance();
    private final TwoRowsEditDistance twoRows = new TwoRowsEditDistance();

    @Test
    void shouldCalculateKnownDistances() {
        assertAllVersions("kitten", "sitting", 3);
        assertAllVersions("flaw", "lawn", 2);
        assertAllVersions("intention", "execution", 5);
        assertAllVersions("", "abc", 3);
        assertAllVersions("abc", "", 3);
        assertAllVersions("abc", "abc", 0);
    }

    @Test
    void shouldHandleRandomStrings() {
        Random random = new Random(42);
        String alphabet = "abc";
        for (int test = 0; test < 100; test++) {
            String first = randomString(random, alphabet);
            String second = randomString(random, alphabet);
            int expected = recursive.calculate(first, second);
            assertEquals(expected, table.calculate(first, second));
            assertEquals(expected, twoRows.calculate(first, second));
        }
    }

    @Test
    void shouldCountOperations() {
        recursive.calculate("abc", "xyz");
        table.calculate("abc", "xyz");
        twoRows.calculate("abc", "xyz");
        assertTrue(recursive.getRecursiveCalls() > 0);
        assertTrue(table.getComputedCells() > 0);
        assertTrue(twoRows.getComputedCells() > 0);
    }
    private void assertAllVersions(String first, String second, int expected) {
        assertEquals(expected, recursive.calculate(first, second));
        assertEquals(expected, table.calculate(first, second));
        assertEquals(expected, twoRows.calculate(first, second));
    }

    private String randomString(Random random, String alphabet) {
        int length = random.nextInt(6);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }
}