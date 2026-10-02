package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        UtcInstant firstInstant = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant equalToFirstInstant = UtcInstant.ofModifiedJulianDay(5L, 20);

        UtcInstant differentNanoOfDay = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant equalToDifferentNanoOfDay = UtcInstant.ofModifiedJulianDay(5L, 30);

        UtcInstant differentModifiedJulianDay = UtcInstant.ofModifiedJulianDay(6L, 20);
        UtcInstant equalToDifferentModifiedJulianDay = UtcInstant.ofModifiedJulianDay(6L, 20);

        new EqualsTester()
                .addEqualityGroup(firstInstant, equalToFirstInstant)
                .addEqualityGroup(differentNanoOfDay, equalToDifferentNanoOfDay)
                .addEqualityGroup(differentModifiedJulianDay, equalToDifferentModifiedJulianDay)
                .testEquals();
    }
}
