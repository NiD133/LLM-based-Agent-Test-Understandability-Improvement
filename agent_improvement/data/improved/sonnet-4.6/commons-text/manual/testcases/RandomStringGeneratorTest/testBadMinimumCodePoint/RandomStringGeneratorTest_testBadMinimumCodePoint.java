package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBadMinimumCodePoint {

    private static final int NEGATIVE_CODE_POINT = -1;

    @Test
    @DisplayName("withinRange() throws IllegalArgumentException when minimum code point is negative")
    void testBadMinimumCodePoint() {
        assertThrowsExactly(
            IllegalArgumentException.class,
            () -> RandomStringGenerator.builder().withinRange(NEGATIVE_CODE_POINT, 1)
        );
    }
}
