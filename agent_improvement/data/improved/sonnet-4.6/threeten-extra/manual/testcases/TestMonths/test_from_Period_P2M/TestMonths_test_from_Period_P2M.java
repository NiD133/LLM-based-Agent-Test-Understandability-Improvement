package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_Period_P2M {

    @Test
    public void test_from_Period_P2M() {
        // Converting a Period of 2 months should yield the same as Months.of(2)
        Period twoMonthPeriod = Period.ofMonths(2);
        Months expected = Months.of(2);
        Months actual = Months.from(twoMonthPeriod);
        assertEquals(expected, actual);
    }
}
