package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestHalf_test_firstMonth {

    @Test
    public void test_firstMonth() {
        assertEquals(Month.JANUARY, Half.H1.firstMonth());
        assertEquals(Month.JULY, Half.H2.firstMonth());
    }
}
