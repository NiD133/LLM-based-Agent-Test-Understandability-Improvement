package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

/**
 * Tests {@link InternationalFixedDate#equals(Object)} and
 * {@link InternationalFixedDate#hashCode()}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_equals_and_hashCode {

    /**
     * Two {@code InternationalFixedDate}s are equal if and only if they refer to the
     * same year, month and day. Each equality group below holds two instances that
     * should be equal to one another but distinct from every other group. The dates
     * are chosen to exercise ordinary days as well as the special Leap Day
     * (month 6, day 29) and Year Day (month 13, day 29).
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(
                InternationalFixedDate.of(2000, 1, 3),
                InternationalFixedDate.of(2000, 1, 3))
            .addEqualityGroup(
                InternationalFixedDate.of(2000, 1, 4),
                InternationalFixedDate.of(2000, 1, 4))
            .addEqualityGroup(
                InternationalFixedDate.of(2000, 2, 3),
                InternationalFixedDate.of(2000, 2, 3))
            .addEqualityGroup(
                InternationalFixedDate.of(2000, 6, 28),
                InternationalFixedDate.of(2000, 6, 28))
            // Leap Day
            .addEqualityGroup(
                InternationalFixedDate.of(2000, 6, 29),
                InternationalFixedDate.of(2000, 6, 29))
            .addEqualityGroup(
                InternationalFixedDate.of(2000, 13, 28),
                InternationalFixedDate.of(2000, 13, 28))
            .addEqualityGroup(
                InternationalFixedDate.of(2001, 1, 1),
                InternationalFixedDate.of(2001, 1, 1))
            // Year Day
            .addEqualityGroup(
                InternationalFixedDate.of(2001, 13, 29),
                InternationalFixedDate.of(2001, 13, 29))
            .addEqualityGroup(
                InternationalFixedDate.of(2004, 6, 29),
                InternationalFixedDate.of(2004, 6, 29))
            .testEquals();
    }
}
