package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the contract between {@link Weeks#equals(Object)} and
 * {@link Weeks#hashCode()}.
 */
public class TestWeeks_test_equals_and_hashCode {

    /**
     * Two {@code Weeks} instances are equal if and only if they hold the same
     * number of weeks. Guava's {@link EqualsTester} checks the full equals/hashCode
     * contract: instances within the same group must be equal and share a hash code,
     * while instances in different groups must not be equal.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Weeks.of(5), Weeks.of(5))
                .addEqualityGroup(Weeks.of(6), Weeks.of(6))
                .testEquals();
    }
}
