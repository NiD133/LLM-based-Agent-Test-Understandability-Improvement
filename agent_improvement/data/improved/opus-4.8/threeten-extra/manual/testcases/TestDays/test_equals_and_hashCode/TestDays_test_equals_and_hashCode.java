package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} and {@code hashCode} contract of {@link Days}.
 */
public class TestDays_test_equals_and_hashCode {

    /**
     * Two {@code Days} instances must be equal (and share a hash code) exactly
     * when they represent the same number of days. Guava's {@link EqualsTester}
     * checks reflexivity, symmetry, transitivity, the hashCode contract, and
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
