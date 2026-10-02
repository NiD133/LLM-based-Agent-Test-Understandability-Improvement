package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Seconds fiveSeconds = Seconds.of(5);

        assertEquals(Seconds.of(0),   fiveSeconds.multipliedBy(0),  "5 * 0 should equal 0");
        assertEquals(Seconds.of(5),   fiveSeconds.multipliedBy(1),  "5 * 1 should equal 5 (identity)");
        assertEquals(Seconds.of(10),  fiveSeconds.multipliedBy(2),  "5 * 2 should equal 10");
        assertEquals(Seconds.of(15),  fiveSeconds.multipliedBy(3),  "5 * 3 should equal 15");
        assertEquals(Seconds.of(-15), fiveSeconds.multipliedBy(-3), "5 * -3 should equal -15 (negative scalar)");
    }
}
