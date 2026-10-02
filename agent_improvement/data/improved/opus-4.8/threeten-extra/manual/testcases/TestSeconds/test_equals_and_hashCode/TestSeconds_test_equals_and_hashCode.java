package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

/**
 * Verifies the {@code equals} / {@code hashCode} contract of {@link Seconds}.
 */
public class TestSeconds_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Seconds built from the same amount must be equal and share a hash code,
        // while Seconds built from different amounts must not be equal.
        new EqualsTester()
                .addEqualityGroup(Seconds.of(5), Seconds.of(5))
                .addEqualityGroup(Seconds.of(6), Seconds.of(6))
                .testEquals();
    }
}
