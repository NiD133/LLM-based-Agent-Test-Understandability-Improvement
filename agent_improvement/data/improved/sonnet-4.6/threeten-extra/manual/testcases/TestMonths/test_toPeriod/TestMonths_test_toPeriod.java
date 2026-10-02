package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_toPeriod {

    @Test
    public void test_toPeriod() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Period.ofMonths(i), Months.of(i).toPeriod());
        }
    }
}
