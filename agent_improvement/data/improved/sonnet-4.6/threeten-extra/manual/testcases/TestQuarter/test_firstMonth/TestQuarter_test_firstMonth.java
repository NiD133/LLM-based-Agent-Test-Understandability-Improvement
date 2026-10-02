package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_firstMonth {

    @Test
    public void test_firstMonth() {
        assertEquals(Month.JANUARY, Quarter.Q1.firstMonth(), "Q1 should start in January");
        assertEquals(Month.APRIL,   Quarter.Q2.firstMonth(), "Q2 should start in April");
        assertEquals(Month.JULY,    Quarter.Q3.firstMonth(), "Q3 should start in July");
        assertEquals(Month.OCTOBER, Quarter.Q4.firstMonth(), "Q4 should start in October");
    }
}
