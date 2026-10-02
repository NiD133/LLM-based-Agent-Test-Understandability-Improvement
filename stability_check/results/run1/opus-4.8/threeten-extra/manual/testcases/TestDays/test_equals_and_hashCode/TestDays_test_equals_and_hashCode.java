package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract of {@link Days}.
 */
public class TestDays_test_equals_and_hashCode {

    /**
     * Two {@code Days} values are equal exactly when they hold the same amount.
     * Guava's {@link EqualsTester} checks the full contract for each equality
     * group: reflexivity, symmetry, consistent hash codes within a group, and
     * inequality across groups.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Days.of(5), Days.of(5))
                .addEqualityGroup(Days.of(6), Days.of(6))
                .testEquals();
    }
}
