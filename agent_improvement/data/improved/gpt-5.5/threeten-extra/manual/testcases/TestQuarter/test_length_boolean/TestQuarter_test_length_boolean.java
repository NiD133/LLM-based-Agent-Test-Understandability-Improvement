package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_length_boolean {

    private static final boolean LEAP_YEAR = true;
    private static final boolean COMMON_YEAR = false;

    @Test
    public void test_length_boolean() {
        assertQuarterLength(Quarter.Q1, 91, 90);
        assertQuarterLength(Quarter.Q2, 91, 91);
        assertQuarterLength(Quarter.Q3, 92, 92);
        assertQuarterLength(Quarter.Q4, 92, 92);
    }

    private static void assertQuarterLength(Quarter quarter, int expectedLeapYearLength, int expectedCommonYearLength) {
        assertEquals(expectedLeapYearLength, quarter.length(LEAP_YEAR));
        assertEquals(expectedCommonYearLength, quarter.length(COMMON_YEAR));
    }
}
