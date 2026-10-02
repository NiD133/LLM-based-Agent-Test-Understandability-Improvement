package org.threeten.extra;

import com.google.common.testing.EqualsTester;
import org.junit.jupiter.api.Test;

public class TestMonths_test_equals_and_hashCode {

    // Verifies that two Months instances with the same count are equal and share a hash code,
    // while instances with different counts are not equal.
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Months.of(5), Months.of(5))
                .addEqualityGroup(Months.of(6), Months.of(6))
                .testEquals();
    }
}
