package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestYears_test_equals_and_hashCode {

    // Verifies that Years instances with equal year counts are equal and share
    // the same hash code, and that instances with different year counts are not equal.
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Years.of(0), Years.of(0))
                .addEqualityGroup(Years.of(1), Years.of(1))
                .testEquals();
    }
}
