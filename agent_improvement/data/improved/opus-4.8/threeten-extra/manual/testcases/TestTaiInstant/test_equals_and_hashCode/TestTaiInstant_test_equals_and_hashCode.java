package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Tests the {@code equals} and {@code hashCode} contract of {@link TaiInstant}.
 */
public class TestTaiInstant_test_equals_and_hashCode {

    /**
     * Verifies that two {@code TaiInstant} values are equal (and share a hash code)
     * exactly when both their seconds and nanoseconds match.
     * <p>
     * Each equality group below holds instants that must be equal to one another and
     * unequal to the instants in every other group. Guava's {@link EqualsTester} checks
     * the full contract: reflexivity, symmetry, hashCode consistency, and inequality
     * against the other groups.
     */
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                // same seconds (5), same nanos (20)
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 20),
                        TaiInstant.ofTaiSeconds(5L, 20))
                // same seconds (5), different nanos (30)
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 30),
                        TaiInstant.ofTaiSeconds(5L, 30))
                // different seconds (6), nanos (20)
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(6L, 20),
                        TaiInstant.ofTaiSeconds(6L, 20))
                .testEquals();
    }
}
