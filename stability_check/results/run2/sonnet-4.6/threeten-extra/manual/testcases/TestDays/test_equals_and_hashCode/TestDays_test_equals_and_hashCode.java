package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestDays_test_equals_and_hashCode {

    // Verifies that Days instances with equal day counts are equal to each other,
    // and that instances with different day counts are not equal.
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Days.of(5), Days.of(5))
                .addEqualityGroup(Days.of(6), Days.of(6))
                .testEquals();
    }
}
