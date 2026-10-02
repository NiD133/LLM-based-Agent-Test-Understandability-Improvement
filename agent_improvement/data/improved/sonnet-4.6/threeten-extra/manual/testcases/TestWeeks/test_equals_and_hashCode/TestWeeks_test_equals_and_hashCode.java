package org.threeten.extra;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestWeeks_test_equals_and_hashCode {

    // Verifies that Weeks instances with the same count are equal and share a hash code,
    // while instances with different counts are not equal.
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Weeks.of(5), Weeks.of(5))
                .addEqualityGroup(Weeks.of(6), Weeks.of(6))
                .testEquals();
    }
}
