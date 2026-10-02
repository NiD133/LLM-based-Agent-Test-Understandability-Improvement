package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBadMinAndMax {

    private static final int MINIMUM_CODE_POINT = 2;
    private static final int MAXIMUM_CODE_POINT = 1;

    @Test
    void testBadMinAndMax() {
        assertThrowsExactly(
                IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(MINIMUM_CODE_POINT, MAXIMUM_CODE_POINT));
    }
}
