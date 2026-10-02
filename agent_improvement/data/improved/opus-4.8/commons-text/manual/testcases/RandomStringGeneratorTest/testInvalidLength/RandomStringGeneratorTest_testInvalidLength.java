package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator#generate(int)} rejects a negative length.
 */
public class RandomStringGeneratorTest_testInvalidLength {

    @Test
    void testInvalidLength() {
        // A negative length is invalid and must raise IllegalArgumentException.
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        assertThrowsExactly(IllegalArgumentException.class, () -> generator.generate(-1));
    }
}
