package org.threeten.extra.chrono;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract for {@link Symmetry010Date}.
 *
 * <p>Each equality group holds two dates built from the same (year, month, day) values.
 * {@link EqualsTester} asserts that dates within a group are equal and share a hash code,
 * while dates in different groups are not equal.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(Symmetry010Date.of(2000, 1, 3), Symmetry010Date.of(2000, 1, 3))
            .addEqualityGroup(Symmetry010Date.of(2000, 1, 4), Symmetry010Date.of(2000, 1, 4))
            .addEqualityGroup(Symmetry010Date.of(2000, 2, 3), Symmetry010Date.of(2000, 2, 3))
            .addEqualityGroup(Symmetry010Date.of(2000, 6, 23), Symmetry010Date.of(2000, 6, 23))
            .addEqualityGroup(Symmetry010Date.of(2000, 6, 28), Symmetry010Date.of(2000, 6, 28))
            .addEqualityGroup(Symmetry010Date.of(2000, 7, 1), Symmetry010Date.of(2000, 7, 1))
            .addEqualityGroup(Symmetry010Date.of(2000, 12, 25), Symmetry010Date.of(2000, 12, 25))
            .addEqualityGroup(Symmetry010Date.of(2000, 12, 28), Symmetry010Date.of(2000, 12, 28))
            .addEqualityGroup(Symmetry010Date.of(2001, 1, 1), Symmetry010Date.of(2001, 1, 1))
            .addEqualityGroup(Symmetry010Date.of(2001, 1, 3), Symmetry010Date.of(2001, 1, 3))
            .addEqualityGroup(Symmetry010Date.of(2001, 12, 28), Symmetry010Date.of(2001, 12, 28))
            .addEqualityGroup(Symmetry010Date.of(2004, 6, 28), Symmetry010Date.of(2004, 6, 28))
            .testEquals();
    }
}
