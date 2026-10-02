package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_toString {

    @Test
    public void test_toString() {
        assertEquals("Q1", Quarter.Q1.toString());
        assertEquals("Q2", Quarter.Q2.toString());
        assertEquals("Q3", Quarter.Q3.toString());
        assertEquals("Q4", Quarter.Q4.toString());
    }
}
