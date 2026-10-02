package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestMinutes_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(Minutes.of(5), Minutes.of(5))
                .addEqualityGroup(Minutes.of(6), Minutes.of(6))
                .testEquals();
    }
}
