package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_Months {

    @Test
    public void test_plus_TemporalAmount_Months() {
        Months base = Months.of(5);

        // Adding zero leaves the value unchanged
        assertEquals(Months.of(5), base.plus(Months.of(0)));

        // Adding a positive amount increases the total
        assertEquals(Months.of(7), base.plus(Months.of(2)));

        // Adding a negative amount decreases the total
        assertEquals(Months.of(3), base.plus(Months.of(-2)));

        // Boundary: (MAX_VALUE - 1) + 1 == MAX_VALUE, no overflow
        assertEquals(Months.of(Integer.MAX_VALUE),
                Months.of(Integer.MAX_VALUE - 1).plus(Months.of(1)));

        // Boundary: (MIN_VALUE + 1) + (-1) == MIN_VALUE, no overflow
        assertEquals(Months.of(Integer.MIN_VALUE),
                Months.of(Integer.MIN_VALUE + 1).plus(Months.of(-1)));
    }
}
