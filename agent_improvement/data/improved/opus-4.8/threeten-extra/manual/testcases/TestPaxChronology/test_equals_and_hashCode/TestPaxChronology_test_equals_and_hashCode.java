package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

/**
 * Verifies {@link PaxDate#equals(Object)} and {@link PaxDate#hashCode()}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_equals_and_hashCode {

    /**
     * Two PaxDates are equal if and only if their year, month and day all match.
     * Each equality group below holds dates that must be equal to each other and
     * unequal to the dates in every other group. Guava's {@link EqualsTester} checks
     * both {@code equals} and {@code hashCode} consistency across all groups.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                // same year, month and day -> equal
                .addEqualityGroup(PaxDate.of(2000, 1, 3), PaxDate.of(2000, 1, 3))
                // differs only by day
                .addEqualityGroup(PaxDate.of(2000, 1, 4), PaxDate.of(2000, 1, 4))
                // differs only by month
                .addEqualityGroup(PaxDate.of(2000, 2, 3), PaxDate.of(2000, 2, 3))
                // differs only by year
                .addEqualityGroup(PaxDate.of(2001, 1, 3), PaxDate.of(2001, 1, 3))
                .testEquals();
    }
}
