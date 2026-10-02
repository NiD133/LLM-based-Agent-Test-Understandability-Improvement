package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract of {@link UtcInstant}.
 *
 * <p>A {@code UtcInstant} is identified by two fields: the Modified Julian Day
 * and the nano-of-day. Two instants are equal only when both fields match, so
 * changing either field must produce an unequal instant.
 */
public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Each equality group holds instants that must be equal to each other
        // and unequal to instants in any other group. Groups differ from one
        // another in exactly one of the two identifying fields.
        new EqualsTester()
                // baseline: day 5, nano-of-day 20
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(5L, 20),
                        UtcInstant.ofModifiedJulianDay(5L, 20))
                // same day, different nano-of-day
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(5L, 30),
                        UtcInstant.ofModifiedJulianDay(5L, 30))
                // different day, same nano-of-day
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(6L, 20),
                        UtcInstant.ofModifiedJulianDay(6L, 20))
                .testEquals();
    }
}
