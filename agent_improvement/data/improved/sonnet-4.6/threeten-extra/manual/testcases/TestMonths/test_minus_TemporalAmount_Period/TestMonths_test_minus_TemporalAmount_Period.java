package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Months fiveMonths = Months.of(5);

        // Subtracting zero months leaves the value unchanged
        assertEquals(Months.of(5), fiveMonths.minus(Period.ofMonths(0)));

        // Subtracting a positive period decreases the count
        assertEquals(Months.of(3), fiveMonths.minus(Period.ofMonths(2)));

        // Subtracting a negative period increases the count
        assertEquals(Months.of(7), fiveMonths.minus(Period.ofMonths(-2)));

        // Boundary: subtracting -1 from (MAX_VALUE - 1) reaches MAX_VALUE without overflow
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(Period.ofMonths(-1)));

        // Boundary: subtracting +1 from (MIN_VALUE + 1) reaches MIN_VALUE without overflow
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(Period.ofMonths(1)));
    }
}
