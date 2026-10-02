package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link RandomStringGenerator#generate(int, int)} rejects a length
 * range whose minimum is greater than its maximum.
 */
public class RandomStringGeneratorTest_testGenerateMinMaxLengthMinGreaterThanMax {

    @Test
    void testGenerateMinMaxLengthMinGreaterThanMax() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        // minLength (1) > maxLength (0) is an invalid range and must be rejected.
        assertThrowsExactly(IllegalArgumentException.class, () -> generator.generate(1, 0));
    }
}
