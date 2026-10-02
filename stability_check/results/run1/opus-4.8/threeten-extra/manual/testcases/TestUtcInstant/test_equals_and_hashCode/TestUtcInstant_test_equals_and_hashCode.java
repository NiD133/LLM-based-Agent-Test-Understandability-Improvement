package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract of {@link UtcInstant}.
 */
public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Two UtcInstants are equal only when both their Modified Julian Day and
        // their nano-of-day match. Each equality group below holds instances that
        // must be equal to each other, but not equal to instances in other groups.
        new EqualsTester()
                // same day (5), same nano-of-day (20)
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(5L, 20),
                        UtcInstant.ofModifiedJulianDay(5L, 20))
                // same day (5), different nano-of-day (30)
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(5L, 30),
                        UtcInstant.ofModifiedJulianDay(5L, 30))
                // different day (6), same nano-of-day (20)
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(6L, 20),
                        UtcInstant.ofModifiedJulianDay(6L, 20))
                .testEquals();
    }
}
