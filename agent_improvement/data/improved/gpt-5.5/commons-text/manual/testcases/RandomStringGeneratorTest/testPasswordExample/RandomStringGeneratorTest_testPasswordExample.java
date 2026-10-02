package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.commons.lang3.ArraySorter;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testPasswordExample {

    private static final char[] PASSWORD_PUNCTUATION = ArraySorter.sort(new char[] { '!', '"', '#', '$', '&', '\'', '(', ')', ',', '.', ':', ';', '?',
            '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~' });

    @Test
    void testPasswordExample() {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .setAccumulate(true)
                .withinRange('a', 'z')
                .withinRange('A', 'Z')
                .withinRange('0', '9')
                .selectFrom(PASSWORD_PUNCTUATION)
                .get();

        final String randomText = generator.generate(10);

        for (final char c : randomText.toCharArray()) {
            assertTrue(isLetterDigitOrConfiguredPunctuation(c));
        }
    }

    private static boolean isLetterDigitOrConfiguredPunctuation(final char c) {
        return Character.isLetter(c) || Character.isDigit(c) || Arrays.binarySearch(PASSWORD_PUNCTUATION, c) >= 0;
    }
}
