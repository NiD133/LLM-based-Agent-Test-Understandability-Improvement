package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBadMaximumCodePoint {

    @Test
    void testBadMaximumCodePoint() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(0, Character.MAX_CODE_POINT + 1));
    }
}
