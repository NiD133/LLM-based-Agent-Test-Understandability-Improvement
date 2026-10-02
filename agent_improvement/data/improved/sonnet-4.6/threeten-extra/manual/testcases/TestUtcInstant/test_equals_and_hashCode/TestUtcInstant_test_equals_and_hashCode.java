package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestUtcInstant_test_equals_and_hashCode {

    // Three distinct UtcInstant values used to form independent equality groups:
    //   Group A: mjDay=5, nanoOfDay=20
    //   Group B: mjDay=5, nanoOfDay=30  (same day, different nano)
    //   Group C: mjDay=6, nanoOfDay=20  (different day, same nano as A)
    @Test
    public void test_equals_and_hashCode() {
        UtcInstant groupA1 = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant groupA2 = UtcInstant.ofModifiedJulianDay(5L, 20);

        UtcInstant groupB1 = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant groupB2 = UtcInstant.ofModifiedJulianDay(5L, 30);

        UtcInstant groupC1 = UtcInstant.ofModifiedJulianDay(6L, 20);
        UtcInstant groupC2 = UtcInstant.ofModifiedJulianDay(6L, 20);

        new EqualsTester()
                .addEqualityGroup(groupA1, groupA2)
                .addEqualityGroup(groupB1, groupB2)
                .addEqualityGroup(groupC1, groupC2)
                .testEquals();
    }
}
