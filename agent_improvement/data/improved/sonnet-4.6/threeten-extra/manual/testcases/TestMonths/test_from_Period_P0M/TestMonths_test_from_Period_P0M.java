package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_Period_P0M {

    // Verifies that converting a zero-month Period via Months.from() yields Months.of(0).
    @Test
    public void test_from_Period_P0M() {
        assertEquals(Months.of(0), Months.from(Period.ofMonths(0)));
    }
}
