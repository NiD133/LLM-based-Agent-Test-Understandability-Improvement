package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(5L, 20),
                        UtcInstant.ofModifiedJulianDay(5L, 20))
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(5L, 30),
                        UtcInstant.ofModifiedJulianDay(5L, 30))
                .addEqualityGroup(
                        UtcInstant.ofModifiedJulianDay(6L, 20),
                        UtcInstant.ofModifiedJulianDay(6L, 20))
                .testEquals();
    }
}
