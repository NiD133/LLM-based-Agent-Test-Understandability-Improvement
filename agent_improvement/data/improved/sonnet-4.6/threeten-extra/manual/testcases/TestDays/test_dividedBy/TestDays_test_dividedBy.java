package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Days twelvedays = Days.of(12);

        // Exact division
        assertEquals(Days.of(12), twelvedays.dividedBy(1));
        assertEquals(Days.of(6),  twelvedays.dividedBy(2));
        assertEquals(Days.of(4),  twelvedays.dividedBy(3));
        assertEquals(Days.of(3),  twelvedays.dividedBy(4));

        // Integer truncation: 12/5=2 and 12/6=2 both truncate toward zero
        assertEquals(Days.of(2),  twelvedays.dividedBy(5));
        assertEquals(Days.of(2),  twelvedays.dividedBy(6));

        // Negative divisor flips sign
        assertEquals(Days.of(-4), twelvedays.dividedBy(-3));
    }
}
