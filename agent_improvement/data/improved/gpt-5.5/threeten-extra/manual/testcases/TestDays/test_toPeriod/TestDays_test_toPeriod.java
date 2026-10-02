package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_toPeriod {

    private static final int FIRST_TESTED_DAY_AMOUNT = -20;
    private static final int LAST_TESTED_DAY_AMOUNT_EXCLUSIVE = 20;

    @Test
    public void test_toPeriod() {
        for (int days = FIRST_TESTED_DAY_AMOUNT; days < LAST_TESTED_DAY_AMOUNT_EXCLUSIVE; days++) {
            assertEquals(Period.ofDays(days), Days.of(days).toPeriod());
        }
    }
}
