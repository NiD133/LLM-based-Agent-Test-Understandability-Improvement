package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_toString {

    @Test
    public void test_toString_positiveDays() {
        Days fiveDays = Days.of(5);
        assertEquals("P5D", fiveDays.toString());
    }

    @Test
    public void test_toString_negativeDays() {
        Days minusOneDay = Days.of(-1);
        assertEquals("P-1D", minusOneDay.toString());
    }
}
