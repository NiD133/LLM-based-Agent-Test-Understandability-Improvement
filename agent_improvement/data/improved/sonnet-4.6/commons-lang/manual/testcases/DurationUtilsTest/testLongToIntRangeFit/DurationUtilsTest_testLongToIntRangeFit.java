package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testLongToIntRangeFit extends AbstractLangTest {

    @Test
    @DisplayName("LONG_TO_INT_RANGE.fit() clamps long values into the int range [Integer.MIN_VALUE, Integer.MAX_VALUE]")
    void testLongToIntRangeFit() {
        // Zero is within range and passes through unchanged
        assertEquals(0, DurationUtils.LONG_TO_INT_RANGE.fit(0L));

        // Values at or below Integer.MIN_VALUE (as long) are clamped to Integer.MIN_VALUE
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 1));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 2));

        // Values at or above Integer.MAX_VALUE (as long) are clamped to Integer.MAX_VALUE
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 1));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 2));

        // Long extremes are also clamped to the int boundaries
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MAX_VALUE));

        // Short boundary values are well within int range and pass through unchanged
        assertEquals(Short.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MIN_VALUE));
        assertEquals(Short.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MAX_VALUE));
    }
}
