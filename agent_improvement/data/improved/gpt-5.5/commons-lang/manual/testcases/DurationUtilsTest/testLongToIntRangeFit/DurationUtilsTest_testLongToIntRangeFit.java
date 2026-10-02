package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testLongToIntRangeFit extends AbstractLangTest {

    @Test
    void testLongToIntRangeFit() {
        assertEquals(0, DurationUtils.LONG_TO_INT_RANGE.fit(0L));

        // Values at and below the int minimum are clamped to Integer.MIN_VALUE.
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 1));
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 2));

        // Values at and above the int maximum are clamped to Integer.MAX_VALUE.
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 1));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 2));

        // Extreme long values are also clamped to the nearest int boundary.
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MAX_VALUE));

        // In-range short values are returned unchanged.
        assertEquals(Short.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MIN_VALUE));
        assertEquals(Short.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MAX_VALUE));
    }
}
