package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateMinMaxLengthInvalidLength {

    @Test
    void testGenerateMinMaxLengthInvalidLength() {
        // A negative minLength is invalid; generate() must reject it immediately.
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        assertThrowsExactly(IllegalArgumentException.class, () -> generator.generate(-1, 0));
    }
}
