package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Months fiveMonths = Months.of(5);
        Months expectedProduct = Months.of(-15);

        assertEquals(expectedProduct, fiveMonths.multipliedBy(-3));
    }
}
