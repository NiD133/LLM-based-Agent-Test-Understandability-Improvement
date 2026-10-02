package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests {@link UtcInstant#equals(Object)} and {@link UtcInstant#hashCode()}.
 */
public class TestUtcInstant_test_equals_and_hashCode {

    /**
     * Two {@code UtcInstant}s are equal only when both their Modified Julian Day
     * and their nano-of-day match. {@link EqualsTester} checks that instances
     * within the same equality group are equal (with consistent hash codes) and
     * that instances in different groups are not.
     */
    @Test
    public void test_equals_and_hashCode() {
        // Group 1: same day (5), same nano-of-day (20)
        UtcInstant day5Nano20 = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant day5Nano20Copy = UtcInstant.ofModifiedJulianDay(5L, 20);

        // Group 2: same day (5) but different nano-of-day (30)
        UtcInstant day5Nano30 = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant day5Nano30Copy = UtcInstant.ofModifiedJulianDay(5L, 30);

        // Group 3: different day (6), same nano-of-day (20) as group 1
        UtcInstant day6Nano20 = UtcInstant.ofModifiedJulianDay(6L, 20);
        UtcInstant day6Nano20Copy = UtcInstant.ofModifiedJulianDay(6L, 20);

        new EqualsTester()
                .addEqualityGroup(day5Nano20, day5Nano20Copy)
                .addEqualityGroup(day5Nano30, day5Nano30Copy)
                .addEqualityGroup(day6Nano20, day6Nano20Copy)
                .testEquals();
    }
}
