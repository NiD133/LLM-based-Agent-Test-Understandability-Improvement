package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Years fiveYears = Years.of(5);

        // Subtracting zero years leaves the value unchanged
        assertEquals(Years.of(5), fiveYears.minus(Period.ofYears(0)));

        // Subtracting a positive period reduces the year count
        assertEquals(Years.of(3), fiveYears.minus(Period.ofYears(2)));

        // Subtracting a negative period increases the year count
        assertEquals(Years.of(7), fiveYears.minus(Period.ofYears(-2)));

        // Subtracting -1 from (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Years.of(Integer.MAX_VALUE),
                Years.of(Integer.MAX_VALUE - 1).minus(Period.ofYears(-1)));

        // Subtracting 1 from (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(Years.of(Integer.MIN_VALUE),
                Years.of(Integer.MIN_VALUE + 1).minus(Period.ofYears(1)));
    }
}
