package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntUnaryOperator;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator.Builder#usingRandom(IntUnaryOperator)}
 * lets the caller plug in a custom source of randomness that drives which characters
 * the generator produces.
 */
public class RandomStringGeneratorTest_testUsingRandomIntUnaryOperator {

    @Test
    void testUsingRandomIntUnaryOperator() {
        // A deterministic "random" source: regardless of the bound it is given,
        // it always returns the code point for 'a'.
        final char expectedChar = 'a';
        final IntUnaryOperator alwaysReturnsA = bound -> expectedChar;

        final String generated = RandomStringGenerator.builder()
                .usingRandom(alwaysReturnsA)
                .get()
                .generate(10);

        // Because the random source is fixed, every generated character must be 'a'.
        for (final char actualChar : generated.toCharArray()) {
            assertEquals(expectedChar, actualChar);
        }
    }
}
