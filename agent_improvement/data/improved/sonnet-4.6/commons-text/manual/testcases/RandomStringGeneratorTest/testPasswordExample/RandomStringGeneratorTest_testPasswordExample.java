package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.commons.lang3.ArraySorter;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testPasswordExample {

    @Test
    void testPasswordExample() {
        // Sorted punctuation characters allowed in the generated password
        final char[] punctuation = ArraySorter.sort(new char[] {
            '!', '"', '#', '$', '&', '\'', '(', ')', ',', '.', ':',
            ';', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'
        });

        // Build a generator that accumulates multiple character ranges:
        // lowercase letters, uppercase letters, digits, and punctuation
        final RandomStringGenerator generator = RandomStringGenerator.builder()
            .setAccumulate(true)
            .withinRange('a', 'z')
            .withinRange('A', 'Z')
            .withinRange('0', '9')
            .selectFrom(punctuation)
            .get();

        final String randomText = generator.generate(10);

        // Every character in the generated string must belong to one of the allowed sets
        for (final char c : randomText.toCharArray()) {
            assertTrue(Character.isLetter(c) || Character.isDigit(c) || Arrays.binarySearch(punctuation, c) >= 0);
        }
    }
}
