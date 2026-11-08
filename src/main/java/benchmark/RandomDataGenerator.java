package benchmark;
import java.util.Random;
public class RandomDataGenerator {
    private final Random random = new Random(42);
    private static final String ALPHABET = "ABC";
    public String randomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHABET.length());
            result.append(ALPHABET.charAt(index));
        }
        return result.toString();
    }
    public String repeatedString(int length, char character) {
        return String.valueOf(character).repeat(length);
    }
    public String noCommonString(int length, char character) {
        return String.valueOf(character).repeat(length);
    }
}