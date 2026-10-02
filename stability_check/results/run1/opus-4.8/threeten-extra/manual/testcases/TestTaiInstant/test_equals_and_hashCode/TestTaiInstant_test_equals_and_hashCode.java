package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@link TaiInstant#equals(Object)} / {@link TaiInstant#hashCode()} contract.
 * <p>
 * Guava's {@link EqualsTester} checks the full contract for each equality group:
 * instances within the same group must be equal (and share a hash code), while
 * instances from different groups must be unequal.
 */
public class TestTaiInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                // group 1: same seconds and nanos -> equal
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 20),
                        TaiInstant.ofTaiSeconds(5L, 20))
                // group 2: same seconds, different nanos -> distinct from group 1
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 30),
                        TaiInstant.ofTaiSeconds(5L, 30))
                // group 3: different seconds -> distinct from groups 1 and 2
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(6L, 20),
                        TaiInstant.ofTaiSeconds(6L, 20))
                .testEquals();
    }
}
