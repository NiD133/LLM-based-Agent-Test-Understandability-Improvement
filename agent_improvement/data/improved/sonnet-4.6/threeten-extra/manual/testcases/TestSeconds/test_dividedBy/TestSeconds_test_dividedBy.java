package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSeconds_test_dividedBy {

    @Test
    @DisplayName("dividedBy truncates toward zero using integer division")
    public void test_dividedBy() {
        Seconds twelveSeconds = Seconds.of(12);

        assertEquals(Seconds.of(12), twelveSeconds.dividedBy(1),  "12 / 1 = 12 (identity)");
        assertEquals(Seconds.of(6),  twelveSeconds.dividedBy(2),  "12 / 2 = 6");
        assertEquals(Seconds.of(4),  twelveSeconds.dividedBy(3),  "12 / 3 = 4");
        assertEquals(Seconds.of(3),  twelveSeconds.dividedBy(4),  "12 / 4 = 3");
        assertEquals(Seconds.of(2),  twelveSeconds.dividedBy(5),  "12 / 5 = 2 (truncated from 2.4)");
        assertEquals(Seconds.of(2),  twelveSeconds.dividedBy(6),  "12 / 6 = 2");
        assertEquals(Seconds.of(-4), twelveSeconds.dividedBy(-3), "12 / -3 = -4 (negative divisor)");
    }
}
