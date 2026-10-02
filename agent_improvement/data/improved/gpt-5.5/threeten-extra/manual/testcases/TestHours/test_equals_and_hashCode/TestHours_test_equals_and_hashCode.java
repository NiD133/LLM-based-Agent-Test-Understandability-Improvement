package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestHours_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Hours.of(5), Hours.of(5))
                .addEqualityGroup(Hours.of(6), Hours.of(6))
                .testEquals();
    }
}
