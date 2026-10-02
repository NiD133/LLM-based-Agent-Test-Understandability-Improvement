package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_equals_and_hashCode {

    /**
     * Verifies that Symmetry010Date correctly implements equals() and hashCode():
     * - Two dates constructed with the same (year, month, day) are equal and share the same hash.
     * - Dates differing in any field (day, month, or year) are not equal.
     *
     * EqualsTester groups dates that must be mutually equal; dates in different groups must be unequal.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            // Same year 2000, month 1 — differ only in day
            .addEqualityGroup(Symmetry010Date.of(2000, 1, 3),  Symmetry010Date.of(2000, 1, 3))
            .addEqualityGroup(Symmetry010Date.of(2000, 1, 4),  Symmetry010Date.of(2000, 1, 4))
            // Same year 2000 — differ only in month
            .addEqualityGroup(Symmetry010Date.of(2000, 2, 3),  Symmetry010Date.of(2000, 2, 3))
            // Mid-year dates in month 6 — differ only in day (within and at end of standard month)
            .addEqualityGroup(Symmetry010Date.of(2000, 6, 23), Symmetry010Date.of(2000, 6, 23))
            .addEqualityGroup(Symmetry010Date.of(2000, 6, 28), Symmetry010Date.of(2000, 6, 28))
            // First day of month 7 (start of Q3)
            .addEqualityGroup(Symmetry010Date.of(2000, 7, 1),  Symmetry010Date.of(2000, 7, 1))
            // End-of-year dates in month 12 — differ only in day
            .addEqualityGroup(Symmetry010Date.of(2000, 12, 25), Symmetry010Date.of(2000, 12, 25))
            .addEqualityGroup(Symmetry010Date.of(2000, 12, 28), Symmetry010Date.of(2000, 12, 28))
            // Year boundary: year 2001 — differ only in year or day
            .addEqualityGroup(Symmetry010Date.of(2001, 1, 1),  Symmetry010Date.of(2001, 1, 1))
            .addEqualityGroup(Symmetry010Date.of(2001, 1, 3),  Symmetry010Date.of(2001, 1, 3))
            .addEqualityGroup(Symmetry010Date.of(2001, 12, 28), Symmetry010Date.of(2001, 12, 28))
            // Leap year 2004 — end of standard month 6
            .addEqualityGroup(Symmetry010Date.of(2004, 6, 28), Symmetry010Date.of(2004, 6, 28))
            .testEquals();
    }
}
