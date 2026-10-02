package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_toString {

    @Test
    public void test_toString() {
        assertQuarterString(Quarter.Q1, "Q1");
        assertQuarterString(Quarter.Q2, "Q2");
        assertQuarterString(Quarter.Q3, "Q3");
        assertQuarterString(Quarter.Q4, "Q4");
    }

    private static void assertQuarterString(Quarter quarter, String expectedString) {
        assertEquals(expectedString, quarter.toString());
    }
}
