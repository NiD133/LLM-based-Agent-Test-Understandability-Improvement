package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);

        // Adding zero months leaves the value unchanged
        assertEquals(Months.of(5), fiveMonths.plus(Period.ofMonths(0)));

        // Adding a positive period increases the month count
        assertEquals(Months.of(7), fiveMonths.plus(Period.ofMonths(2)));

        // Adding a negative period decreases the month count
        assertEquals(Months.of(3), fiveMonths.plus(Period.ofMonths(-2)));

        // Adding 1 to (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(Period.ofMonths(1)));

        // Adding -1 to (MIN_VALUE + 1) reaches Integer.MIN_VALUE without underflow
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(Period.ofMonths(-1)));
    }
}
