package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Days fiveDays = Days.of(5);

        // Adding zero days leaves the value unchanged
        assertEquals(Days.of(5), fiveDays.plus(Period.ofDays(0)));

        // Adding a positive period increases the day count
        assertEquals(Days.of(7), fiveDays.plus(Period.ofDays(2)));

        // Adding a negative period decreases the day count
        assertEquals(Days.of(3), fiveDays.plus(Period.ofDays(-2)));

        // Adding 1 to (MAX_VALUE - 1) reaches exactly Integer.MAX_VALUE without overflow
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(Period.ofDays(1)));

        // Adding -1 to (MIN_VALUE + 1) reaches exactly Integer.MIN_VALUE without overflow
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(Period.ofDays(-1)));
    }
}
