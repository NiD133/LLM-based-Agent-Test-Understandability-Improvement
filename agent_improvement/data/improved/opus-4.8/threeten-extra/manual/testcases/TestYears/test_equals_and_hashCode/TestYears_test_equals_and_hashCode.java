package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals}/{@code hashCode} contract of {@link Years}.
 */
public class TestYears_test_equals_and_hashCode {

    /**
     * Two {@code Years} instances are equal if and only if they hold the same
     * amount. Guava's {@link EqualsTester} checks reflexivity, symmetry and the
     * {@code equals}/{@code hashCode} consistency across the supplied groups:
     * instances within a group must be equal, while instances in different
     * groups must not be.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Years.of(0), Years.of(0))
                .addEqualityGroup(Years.of(1), Years.of(1))
                .testEquals();
    }
}
