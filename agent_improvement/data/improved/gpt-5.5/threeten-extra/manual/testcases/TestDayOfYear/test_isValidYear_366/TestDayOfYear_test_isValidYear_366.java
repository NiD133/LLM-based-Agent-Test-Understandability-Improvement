package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_isValidYear_366 {

    @Test
    public void test_isValidYear_366() {
        DayOfYear leapDay = DayOfYear.of(366);

        assertEquals(false, leapDay.isValidYear(2011));
        assertEquals(true, leapDay.isValidYear(2012));
        assertEquals(false, leapDay.isValidYear(2013));
    }
}
