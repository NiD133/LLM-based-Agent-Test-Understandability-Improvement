package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        // Dividing by a negative divisor: 12 / -3 = -4
        Months twelveMonths = Months.of(12);
        assertEquals(Months.of(-4), twelveMonths.dividedBy(-3));
    }
}
