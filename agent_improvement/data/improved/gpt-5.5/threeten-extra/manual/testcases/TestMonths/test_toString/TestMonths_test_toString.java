package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_toString {

    @Test
    public void test_toString() {
        Months fiveMonths = Months.of(5);
        assertEquals("P5M", fiveMonths.toString());

        Months negativeOneMonth = Months.of(-1);
        assertEquals("P-1M", negativeOneMonth.toString());
    }
}
