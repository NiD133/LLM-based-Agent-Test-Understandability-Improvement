package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testGenerateMinMaxLengthInvalidLength {

    private static final int NEGATIVE_MINIMUM_LENGTH = -1;
    private static final int MAXIMUM_LENGTH = 0;

    @Test
    void testGenerateMinMaxLengthInvalidLength() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        assertThrowsExactly(IllegalArgumentException.class,
                () -> generator.generate(NEGATIVE_MINIMUM_LENGTH, MAXIMUM_LENGTH));
    }
}
