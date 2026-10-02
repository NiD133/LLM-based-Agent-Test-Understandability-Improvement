package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_enum {

    @Test
    public void test_enum() {
        assertEquals(Quarter.Q4, Quarter.valueOf("Q4"));
        assertEquals(Quarter.Q1, Quarter.values()[0]);
    }
}
