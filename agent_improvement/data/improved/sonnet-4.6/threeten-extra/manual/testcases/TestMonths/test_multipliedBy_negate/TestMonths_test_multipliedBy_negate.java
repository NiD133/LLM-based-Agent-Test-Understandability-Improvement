package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        // Multiplying 5 months by -3 should yield -15 months
        Months fiveMonths = Months.of(5);
        Months result = fiveMonths.multipliedBy(-3);
        assertEquals(Months.of(-15), result);
    }
}
