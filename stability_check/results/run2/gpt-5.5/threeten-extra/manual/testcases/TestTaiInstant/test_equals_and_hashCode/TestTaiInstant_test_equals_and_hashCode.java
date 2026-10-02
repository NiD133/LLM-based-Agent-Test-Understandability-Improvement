package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestTaiInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 20),
                        TaiInstant.ofTaiSeconds(5L, 20))
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(5L, 30),
                        TaiInstant.ofTaiSeconds(5L, 30))
                .addEqualityGroup(
                        TaiInstant.ofTaiSeconds(6L, 20),
                        TaiInstant.ofTaiSeconds(6L, 20))
                .testEquals();
    }
}
