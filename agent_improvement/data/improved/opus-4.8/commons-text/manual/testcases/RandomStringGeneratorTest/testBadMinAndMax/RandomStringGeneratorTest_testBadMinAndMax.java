package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link RandomStringGenerator.Builder#withinRange(int, int)} rejects a
 * range whose minimum code point is greater than its maximum code point.
 */
public class RandomStringGeneratorTest_testBadMinAndMax {

    @Test
    void withinRangeRejectsMinimumGreaterThanMaximum() {
        final int minimumCodePoint = 2;
        final int maximumCodePoint = 1;

        // The minimum (2) exceeds the maximum (1), so the builder must reject the range.
        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(minimumCodePoint, maximumCodePoint));
    }
}
