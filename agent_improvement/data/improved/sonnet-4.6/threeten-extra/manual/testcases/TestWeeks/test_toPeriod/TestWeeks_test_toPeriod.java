package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_toPeriod {

    @Test
    public void test_toPeriod() {
        for (int i = -20; i < 20; i++) {
            assertEquals(Period.ofWeeks(i), Weeks.of(i).toPeriod());
        }
    }
}
