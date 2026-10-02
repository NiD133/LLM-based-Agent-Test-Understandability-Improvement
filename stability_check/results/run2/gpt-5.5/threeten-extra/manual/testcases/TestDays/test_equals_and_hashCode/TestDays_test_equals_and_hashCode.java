package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestDays_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        Days fiveDays = Days.of(5);
        Days anotherFiveDays = Days.of(5);
        Days sixDays = Days.of(6);
        Days anotherSixDays = Days.of(6);

        new EqualsTester()
                .addEqualityGroup(fiveDays, anotherFiveDays)
                .addEqualityGroup(sixDays, anotherSixDays)
                .testEquals();
    }
}
