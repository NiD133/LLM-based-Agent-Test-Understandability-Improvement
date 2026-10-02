package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@link Hours#equals(Object)} / {@link Hours#hashCode()} contract.
 */
public class TestHours_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Two Hours instances are equal if and only if they hold the same amount.
        // Each equality group lists values that must be equal to one another and
        // unequal to the values in every other group; Guava's EqualsTester also
        // checks the hashCode and reflexive/symmetric/null contracts.
        new EqualsTester()
                .addEqualityGroup(Hours.of(5), Hours.of(5))
                .addEqualityGroup(Hours.of(6), Hours.of(6))
                .testEquals();
    }
}
