package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;
import com.google.common.testing.EqualsTester;

public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // EqualsTester verifies: instances in the same group are equal to each other,
        // instances in different groups are not equal, and hashCode is consistent with equals.
        //
        // Three distinct UtcInstant values are defined by (mjDay, nanoOfDay):
        //   Group A: mjDay=5, nanoOfDay=20  — same day, lower nano
        //   Group B: mjDay=5, nanoOfDay=30  — same day, higher nano  (differs from A by nanoOfDay)
        //   Group C: mjDay=6, nanoOfDay=20  — next day, same nano    (differs from A by mjDay)
        UtcInstant instantA1 = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant instantA2 = UtcInstant.ofModifiedJulianDay(5L, 20);

        UtcInstant instantB1 = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant instantB2 = UtcInstant.ofModifiedJulianDay(5L, 30);

        UtcInstant instantC1 = UtcInstant.ofModifiedJulianDay(6L, 20);
        UtcInstant instantC2 = UtcInstant.ofModifiedJulianDay(6L, 20);

        new EqualsTester()
                .addEqualityGroup(instantA1, instantA2)
                .addEqualityGroup(instantB1, instantB2)
                .addEqualityGroup(instantC1, instantC2)
                .testEquals();
    }
}
