package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_compareTo {

    @Test
    public void test_compareTo() {
        Months fiveMonths = Months.of(5);
        Months sixMonths = Months.of(6);

        assertEquals(0, fiveMonths.compareTo(fiveMonths), "Comparing a Months instance to itself should return 0");
        assertEquals(-1, fiveMonths.compareTo(sixMonths), "5 months is less than 6 months, so compareTo should return negative");
        assertEquals(1, sixMonths.compareTo(fiveMonths), "6 months is greater than 5 months, so compareTo should return positive");
    }
}
