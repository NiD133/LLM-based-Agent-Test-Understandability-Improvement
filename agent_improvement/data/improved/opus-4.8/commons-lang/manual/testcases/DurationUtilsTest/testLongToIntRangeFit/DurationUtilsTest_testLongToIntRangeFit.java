package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#LONG_TO_INT_RANGE}, the range used to clamp a {@code long}
 * value into the {@code int} range.
 *
 * <p>The range spans {@code [NumberUtils.LONG_INT_MIN_VALUE, NumberUtils.LONG_INT_MAX_VALUE]}
 * (i.e. {@code Integer.MIN_VALUE} to {@code Integer.MAX_VALUE} expressed as longs). Calling
 * {@code fit(long)} returns:</p>
 * <ul>
 *   <li>the value itself, when it already lies within the int range;</li>
 *   <li>{@code Integer.MIN_VALUE}, when the value is below the int range;</li>
 *   <li>{@code Integer.MAX_VALUE}, when the value is above the int range.</li>
 * </ul>
 */
public class DurationUtilsTest_testLongToIntRangeFit extends AbstractLangTest {

    /** Lower bound of the int range, as a long. */
    private static final long INT_MIN_AS_LONG = NumberUtils.LONG_INT_MIN_VALUE;

    /** Upper bound of the int range, as a long. */
    private static final long INT_MAX_AS_LONG = NumberUtils.LONG_INT_MAX_VALUE;

    /**
     * Asserts that clamping {@code input} into the int range yields {@code expected}.
     *
     * @param expected the expected clamped result
     * @param input    the long value to clamp
     */
    private static void assertClampedTo(final long expected, final long input) {
        assertEquals(expected, DurationUtils.LONG_TO_INT_RANGE.fit(input));
    }

    @Test
    void testLongToIntRangeFit() {
        // A value inside the range is returned unchanged.
        assertClampedTo(0, 0L);

        // At and below the lower bound: clamped up to Integer.MIN_VALUE.
        assertClampedTo(Integer.MIN_VALUE, INT_MIN_AS_LONG);
        assertClampedTo(Integer.MIN_VALUE, INT_MIN_AS_LONG - 1);
        assertClampedTo(Integer.MIN_VALUE, INT_MIN_AS_LONG - 2);

        // At and above the upper bound: clamped down to Integer.MAX_VALUE.
        assertClampedTo(Integer.MAX_VALUE, INT_MAX_AS_LONG);
        assertClampedTo(Integer.MAX_VALUE, INT_MAX_AS_LONG + 1);
        assertClampedTo(Integer.MAX_VALUE, INT_MAX_AS_LONG + 2);

        // Extreme long bounds are clamped to the int bounds.
        assertClampedTo(Integer.MIN_VALUE, Long.MIN_VALUE);
        assertClampedTo(Integer.MAX_VALUE, Long.MAX_VALUE);

        // Values comfortably inside the range (the short bounds) pass through unchanged.
        assertClampedTo(Short.MIN_VALUE, Short.MIN_VALUE);
        assertClampedTo(Short.MAX_VALUE, Short.MAX_VALUE);
    }
}
