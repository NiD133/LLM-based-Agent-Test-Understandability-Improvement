package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_toPeriod {

    private static final int FIRST_TESTED_YEAR = -20;
    private static final int FIRST_UNTESTED_YEAR = 20;

    @Test
    public void test_toPeriod() {
        for (int years = FIRST_TESTED_YEAR; years < FIRST_UNTESTED_YEAR; years++) {
            assertEquals(Period.ofYears(years), Years.of(years).toPeriod());
        }
    }
}
