package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Seconds twelveSeconds = Seconds.of(12);

        assertEquals(Seconds.of(-4), twelveSeconds.dividedBy(-3));
    }
}
