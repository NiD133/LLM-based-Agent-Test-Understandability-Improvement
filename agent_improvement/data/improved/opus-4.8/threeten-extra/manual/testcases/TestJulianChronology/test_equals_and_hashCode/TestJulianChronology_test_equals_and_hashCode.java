package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests {@link JulianDate#equals(Object)} and {@link JulianDate#hashCode()}.
 */
public class TestJulianChronology_test_equals_and_hashCode {

    /**
     * Two {@code JulianDate}s are equal if and only if they share the same
     * year, month and day. Each equality group below contains two dates that
     * must be equal to each other, while dates in different groups must not be.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(JulianDate.of(2000, 1, 3), JulianDate.of(2000, 1, 3))
                .addEqualityGroup(JulianDate.of(2000, 1, 4), JulianDate.of(2000, 1, 4))
                .addEqualityGroup(JulianDate.of(2000, 2, 3), JulianDate.of(2000, 2, 3))
                .addEqualityGroup(JulianDate.of(2001, 1, 3), JulianDate.of(2001, 1, 3))
                .testEquals();
    }
}
