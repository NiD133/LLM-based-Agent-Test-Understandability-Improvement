package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_int {

    @Test
    public void test_minus_int() {
        Months fiveMonths = Months.of(5);

        assertEquals(Months.of(5), fiveMonths.minus(0));
        assertEquals(Months.of(3), fiveMonths.minus(2));
        assertEquals(Months.of(7), fiveMonths.minus(-2));

        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Months.of(Integer.MIN_VALUE), Months.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
