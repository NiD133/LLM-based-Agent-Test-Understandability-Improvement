package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_Period_P0M {

    @Test
    public void test_from_Period_P0M() {
        assertEquals(Months.of(0), Months.from(Period.ofMonths(0)));
    }
}
