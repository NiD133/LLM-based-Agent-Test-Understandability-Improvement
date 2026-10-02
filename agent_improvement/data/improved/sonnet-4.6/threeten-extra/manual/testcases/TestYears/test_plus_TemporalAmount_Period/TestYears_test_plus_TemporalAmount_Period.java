package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Years base = Years.of(5);

        // Adding zero years leaves the value unchanged
        assertEquals(Years.of(5), base.plus(Period.ofYears(0)));

        // Adding a positive period increases the year count
        assertEquals(Years.of(7), base.plus(Period.ofYears(2)));

        // Adding a negative period decreases the year count
        assertEquals(Years.of(3), base.plus(Period.ofYears(-2)));

        // Adding 1 to MAX_VALUE - 1 reaches exactly Integer.MAX_VALUE (boundary check)
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(Period.ofYears(1)));

        // Adding -1 to MIN_VALUE + 1 reaches exactly Integer.MIN_VALUE (boundary check)
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(Period.ofYears(-1)));
    }
}
