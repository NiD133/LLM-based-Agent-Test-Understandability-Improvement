package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_compareTo {

    @Test
    public void test_compareTo() {
        Months fiveMonths = Months.of(5);
        Months sixMonths = Months.of(6);

        assertEquals(0, fiveMonths.compareTo(fiveMonths));
        assertEquals(-1, fiveMonths.compareTo(sixMonths));
        assertEquals(1, sixMonths.compareTo(fiveMonths));
    }
}
