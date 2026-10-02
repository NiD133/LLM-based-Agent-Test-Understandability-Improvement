package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestBritishCutoverChronology_test_equals_and_hashCode {

    @Test
    public void test_equals_and_hashCode() {
        // Each addEqualityGroup defines a set of dates that must be equal to each other
        // but not equal to dates in any other group.
        BritishCutoverDate date_2000_Jan_3a = BritishCutoverDate.of(2000, 1, 3);
        BritishCutoverDate date_2000_Jan_3b = BritishCutoverDate.of(2000, 1, 3);

        BritishCutoverDate date_2000_Jan_4a = BritishCutoverDate.of(2000, 1, 4);
        BritishCutoverDate date_2000_Jan_4b = BritishCutoverDate.of(2000, 1, 4);

        BritishCutoverDate date_2000_Feb_3a = BritishCutoverDate.of(2000, 2, 3);
        BritishCutoverDate date_2000_Feb_3b = BritishCutoverDate.of(2000, 2, 3);

        BritishCutoverDate date_2001_Jan_3a = BritishCutoverDate.of(2001, 1, 3);
        BritishCutoverDate date_2001_Jan_3b = BritishCutoverDate.of(2001, 1, 3);

        new EqualsTester()
                .addEqualityGroup(date_2000_Jan_3a, date_2000_Jan_3b)
                .addEqualityGroup(date_2000_Jan_4a, date_2000_Jan_4b)
                .addEqualityGroup(date_2000_Feb_3a, date_2000_Feb_3b)
                .addEqualityGroup(date_2001_Jan_3a, date_2001_Jan_3b)
                .testEquals();
    }
}
