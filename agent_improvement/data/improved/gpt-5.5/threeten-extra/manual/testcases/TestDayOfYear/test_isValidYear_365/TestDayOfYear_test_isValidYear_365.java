package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_isValidYear_365 {

    @Test
    public void test_isValidYear_365() {
        DayOfYear day365 = DayOfYear.of(365);

        assertTrue(day365.isValidYear(2011));
        assertTrue(day365.isValidYear(2012));
        assertTrue(day365.isValidYear(2013));
    }
}
