package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} and {@code hashCode} contract of {@link Days}.
 */
public class TestDays_test_equals_and_hashCode {

    /**
     * Two {@code Days} values are equal only when they hold the same amount.
     * <p>
     * Guava's {@link EqualsTester} checks the full contract for each equality
     * group: instances within a group must be equal (and share a hash code),
     * while instances in different groups must not be equal.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Days.of(5), Days.of(5))
                .addEqualityGroup(Days.of(6), Days.of(6))
                .testEquals();
    }
}
