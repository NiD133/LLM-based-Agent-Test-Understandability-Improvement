package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(5), fiveYears.plus(Period.ofYears(0)));
        assertEquals(Years.of(7), fiveYears.plus(Period.ofYears(2)));
        assertEquals(Years.of(3), fiveYears.plus(Period.ofYears(-2)));

        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(Period.ofYears(1)));
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(Period.ofYears(-1)));
    }
}
