package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.commons.lang3.ArraySorter;
import org.junit.jupiter.api.Test;

/**
 * Verifies the "password" usage example for {@link RandomStringGenerator}: a generator that draws
 * from letters, digits and a fixed set of punctuation characters should only ever emit characters
 * from those three groups.
 */
public class RandomStringGeneratorTest_testPasswordExample {

    /** Punctuation characters allowed in the generated password, kept sorted for binary search. */
    private static final char[] ALLOWED_PUNCTUATION = ArraySorter.sort(new char[] {
        '!', '"', '#', '$', '&', '\'', '(', ')', ',', '.', ':', ';',
        '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'
    });

    @Test
    void testPasswordExample() {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
            .setAccumulate(true)
            .withinRange('a', 'z')
            .withinRange('A', 'Z')
            .withinRange('0', '9')
            .selectFrom(ALLOWED_PUNCTUATION)
            .get();

        final String password = generator.generate(10);

        for (final char c : password.toCharArray()) {
            final boolean isAllowedCharacter =
                Character.isLetter(c)
                || Character.isDigit(c)
                || Arrays.binarySearch(ALLOWED_PUNCTUATION, c) >= 0;
            assertTrue(isAllowedCharacter,
                "Generated password contained an unexpected character: '" + c + "'");
        }
    }
}
