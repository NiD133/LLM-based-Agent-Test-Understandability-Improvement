package org.threeten.extra;

import com.google.common.testing.EqualsTester;
import org.junit.jupiter.api.Test;

public class TestDays_test_equals_and_hashCode {

    // Verifies that Days instances with the same value are equal to each other
    // and that instances with different values are not equal.
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Days.of(5), Days.of(5))
                .addEqualityGroup(Days.of(6), Days.of(6))
                .testEquals();
    }
}
