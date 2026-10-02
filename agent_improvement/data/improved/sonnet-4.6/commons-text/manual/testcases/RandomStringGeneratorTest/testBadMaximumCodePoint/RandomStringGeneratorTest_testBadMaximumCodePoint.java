package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBadMaximumCodePoint {

    // One beyond the highest valid Unicode code point — must be rejected
    private static final int INVALID_MAX_CODE_POINT = Character.MAX_CODE_POINT + 1;

    @Test
    void testBadMaximumCodePoint() {
        assertThrowsExactly(
            IllegalArgumentException.class,
            () -> RandomStringGenerator.builder().withinRange(0, INVALID_MAX_CODE_POINT)
        );
    }
}
