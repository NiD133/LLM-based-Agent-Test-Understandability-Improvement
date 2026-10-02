package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_int {

    @Test
    public void test_plus_int() {
        Months test5 = Months.of(5);

        // Adding zero returns the same value
        assertEquals(Months.of(5), test5.plus(0));

        // Adding a positive number increases the month count
        assertEquals(Months.of(7), test5.plus(2));

        // Adding a negative number decreases the month count
        assertEquals(Months.of(3), test5.plus(-2));

        // Adding 1 to MAX_VALUE - 1 reaches Integer.MAX_VALUE without overflow
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).plus(1));

        // Adding -1 to MIN_VALUE + 1 reaches Integer.MIN_VALUE without overflow
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
