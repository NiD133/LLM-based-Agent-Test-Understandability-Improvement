package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract of {@link TaiInstant}.
 *
 * <p>Two {@code TaiInstant} values are equal only when both their TAI-seconds and
 * their nano-of-second fields match. Guava's {@link EqualsTester} checks the full
 * contract: instances within the same equality group must be equal (and share a
 * hash code), while instances in different groups must not be equal.
 */
public class TestTaiInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                // same seconds (5) and same nanos (20)
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 20),
                        TaiInstant.ofTaiSeconds(5L, 20))
                // same seconds (5) but different nanos (30)
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 30),
                        TaiInstant.ofTaiSeconds(5L, 30))
                // different seconds (6) with nanos (20)
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(6L, 20),
                        TaiInstant.ofTaiSeconds(6L, 20))
                .testEquals();
    }
}
