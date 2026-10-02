package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateMinMaxLengthInvalidLength {

    /**
     * Verifies that {@link RandomStringGenerator#generate(int, int)} rejects a
     * negative minimum length. Calling it with a minimum of {@code -1} must throw
     * an {@link IllegalArgumentException}.
     */
    @Test
    void testGenerateMinMaxLengthInvalidLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        final int negativeMinLength = -1;
        final int maxLength = 0;

        assertThrowsExactly(IllegalArgumentException.class,
                () -> generator.generate(negativeMinLength, maxLength));
    }
}
