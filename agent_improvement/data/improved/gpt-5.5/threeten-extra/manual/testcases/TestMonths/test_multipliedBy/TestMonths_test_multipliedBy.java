package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Months fiveMonths = Months.of(5);

        assertEquals(Months.of(0), fiveMonths.multipliedBy(0));
        assertEquals(Months.of(5), fiveMonths.multipliedBy(1));
        assertEquals(Months.of(10), fiveMonths.multipliedBy(2));
        assertEquals(Months.of(15), fiveMonths.multipliedBy(3));
        assertEquals(Months.of(-15), fiveMonths.multipliedBy(-3));
    }
}
