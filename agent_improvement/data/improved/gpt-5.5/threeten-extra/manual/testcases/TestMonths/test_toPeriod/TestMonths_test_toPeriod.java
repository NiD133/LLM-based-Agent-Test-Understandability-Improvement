package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_toPeriod {

    @Test
    public void test_toPeriod() {
        for (int months = -20; months < 20; months++) {
            Period expectedPeriod = Period.ofMonths(months);
            Period actualPeriod = Months.of(months).toPeriod();

            assertEquals(expectedPeriod, actualPeriod);
        }
    }
}
