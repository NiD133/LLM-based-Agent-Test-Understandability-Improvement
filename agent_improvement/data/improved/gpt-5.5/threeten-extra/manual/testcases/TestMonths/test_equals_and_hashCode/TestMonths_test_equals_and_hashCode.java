package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestMonths_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Months.of(5), Months.of(5))
                .addEqualityGroup(Months.of(6), Months.of(6))
                .testEquals();
    }
}
