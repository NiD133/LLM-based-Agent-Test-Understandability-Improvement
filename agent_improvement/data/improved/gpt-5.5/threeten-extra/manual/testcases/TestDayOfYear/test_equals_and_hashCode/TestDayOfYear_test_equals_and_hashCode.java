package org.threeten.extra;

import org.junit.jupiter.api.Test;

import com.google.common.testing.EqualsTester;

public class TestDayOfYear_test_equals_and_hashCode {

    private static final int FIRST_DAY_OF_YEAR = 1;
    private static final int LAST_DAY_OF_LEAP_YEAR = 366;

    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();

        for (int dayOfYear = FIRST_DAY_OF_YEAR; dayOfYear <= LAST_DAY_OF_LEAP_YEAR; dayOfYear++) {
            equalsTester.addEqualityGroup(DayOfYear.of(dayOfYear), DayOfYear.of(dayOfYear));
        }

        equalsTester.testEquals();
    }
}
