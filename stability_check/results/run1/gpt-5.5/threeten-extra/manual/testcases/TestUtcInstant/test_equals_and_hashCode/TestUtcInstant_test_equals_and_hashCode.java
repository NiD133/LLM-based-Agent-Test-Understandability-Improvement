package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        UtcInstant sameDayAndNanos = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant equalSameDayAndNanos = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant sameDayDifferentNanos = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant equalSameDayDifferentNanos = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant differentDaySameNanos = UtcInstant.ofModifiedJulianDay(6L, 20);
        UtcInstant equalDifferentDaySameNanos = UtcInstant.ofModifiedJulianDay(6L, 20);

        new EqualsTester()
                .addEqualityGroup(sameDayAndNanos, equalSameDayAndNanos)
                .addEqualityGroup(sameDayDifferentNanos, equalSameDayDifferentNanos)
                .addEqualityGroup(differentDaySameNanos, equalDifferentDaySameNanos)
                .testEquals();
    }
}
