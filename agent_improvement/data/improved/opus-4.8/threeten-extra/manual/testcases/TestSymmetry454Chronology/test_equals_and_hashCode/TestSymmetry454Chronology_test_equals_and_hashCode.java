package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract for {@link Symmetry454Date}.
 *
 * <p>Each equality group below holds two independently constructed dates that
 * represent the same point in time, so they must be equal to each other and
 * unequal to the dates in every other group. {@link EqualsTester} checks all of
 * these relationships (reflexivity, symmetry, and consistent hash codes) for us.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                // Same year and month, differing day-of-month
                .addEqualityGroup(Symmetry454Date.of(2000, 1, 3), Symmetry454Date.of(2000, 1, 3))
                .addEqualityGroup(Symmetry454Date.of(2000, 1, 4), Symmetry454Date.of(2000, 1, 4))
                // Same year, differing month
                .addEqualityGroup(Symmetry454Date.of(2000, 2, 3), Symmetry454Date.of(2000, 2, 3))
                // Same month and day, differing year
                .addEqualityGroup(Symmetry454Date.of(2001, 1, 3), Symmetry454Date.of(2001, 1, 3))
                // Last days of December (28-day vs. 25-day distinctions)
                .addEqualityGroup(Symmetry454Date.of(2000, 12, 28), Symmetry454Date.of(2000, 12, 28))
                .addEqualityGroup(Symmetry454Date.of(2000, 12, 25), Symmetry454Date.of(2000, 12, 25))
                // Year boundaries
                .addEqualityGroup(Symmetry454Date.of(2001, 1, 1), Symmetry454Date.of(2001, 1, 1))
                .addEqualityGroup(Symmetry454Date.of(2001, 12, 28), Symmetry454Date.of(2001, 12, 28))
                // Mid-year days around a month boundary
                .addEqualityGroup(Symmetry454Date.of(2000, 6, 28), Symmetry454Date.of(2000, 6, 28))
                .addEqualityGroup(Symmetry454Date.of(2000, 6, 23), Symmetry454Date.of(2000, 6, 23))
                .addEqualityGroup(Symmetry454Date.of(2000, 7, 1), Symmetry454Date.of(2000, 7, 1))
                // A different year, to keep groups distinct
                .addEqualityGroup(Symmetry454Date.of(2004, 6, 28), Symmetry454Date.of(2004, 6, 28))
                .testEquals();
    }
}
