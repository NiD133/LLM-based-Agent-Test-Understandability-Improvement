package org.threeten.extra.scale;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestUtcInstant_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Three distinct instants: different combinations of MJD day and nano-of-day.
        // Instants within each group share the same (mjDay, nanoOfDay) and must be equal;
        // instants from different groups must be unequal.
        UtcInstant day5_nano20_a = UtcInstant.ofModifiedJulianDay(5L, 20);
        UtcInstant day5_nano20_b = UtcInstant.ofModifiedJulianDay(5L, 20);

        UtcInstant day5_nano30_a = UtcInstant.ofModifiedJulianDay(5L, 30);
        UtcInstant day5_nano30_b = UtcInstant.ofModifiedJulianDay(5L, 30);

        UtcInstant day6_nano20_a = UtcInstant.ofModifiedJulianDay(6L, 20);
        UtcInstant day6_nano20_b = UtcInstant.ofModifiedJulianDay(6L, 20);

        new EqualsTester()
                .addEqualityGroup(day5_nano20_a, day5_nano20_b)  // same day, same nano
                .addEqualityGroup(day5_nano30_a, day5_nano30_b)  // same day, different nano
                .addEqualityGroup(day6_nano20_a, day6_nano20_b)  // different day, same nano
                .testEquals();
    }
}
