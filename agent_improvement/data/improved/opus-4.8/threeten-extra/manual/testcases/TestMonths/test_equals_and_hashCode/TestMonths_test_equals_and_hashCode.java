package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests the {@link Months#equals(Object)} and {@link Months#hashCode()} contract.
 */
public class TestMonths_test_equals_and_hashCode {

    /**
     * Two {@code Months} instances are equal if and only if they hold the same amount.
     * <p>
     * Guava's {@link EqualsTester} verifies the full equals/hashCode contract:
     * instances within the same equality group must be equal and share a hash code,
     * while instances in different groups must not be equal.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Months.of(5), Months.of(5))
                .addEqualityGroup(Months.of(6), Months.of(6))
                .testEquals();
    }
}
