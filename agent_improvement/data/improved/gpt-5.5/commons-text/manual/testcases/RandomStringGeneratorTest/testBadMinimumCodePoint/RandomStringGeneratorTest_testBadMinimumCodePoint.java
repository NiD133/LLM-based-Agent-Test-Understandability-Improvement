package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBadMinimumCodePoint {

    @Test
    void withinRangeRejectsNegativeMinimumCodePoint() {
        assertThrowsExactly(IllegalArgumentException.class, () -> RandomStringGenerator.builder().withinRange(-1, 1));
    }
}
