package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_toString {

    @Test
    public void test_toString() {
        Days positiveDays = Days.of(5);
        assertEquals("P5D", positiveDays.toString());

        Days negativeDays = Days.of(-1);
        assertEquals("P-1D", negativeDays.toString());
    }
}
