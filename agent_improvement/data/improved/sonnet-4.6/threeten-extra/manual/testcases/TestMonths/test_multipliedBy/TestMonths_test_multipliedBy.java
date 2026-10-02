package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Months fiveMonths = Months.of(5);

        assertEquals(Months.of(0),   fiveMonths.multipliedBy(0));   // any value × 0 = zero
        assertEquals(Months.of(5),   fiveMonths.multipliedBy(1));   // × 1 is identity
        assertEquals(Months.of(10),  fiveMonths.multipliedBy(2));   // double
        assertEquals(Months.of(15),  fiveMonths.multipliedBy(3));   // triple
        assertEquals(Months.of(-15), fiveMonths.multipliedBy(-3));  // negative scalar negates
    }
}
