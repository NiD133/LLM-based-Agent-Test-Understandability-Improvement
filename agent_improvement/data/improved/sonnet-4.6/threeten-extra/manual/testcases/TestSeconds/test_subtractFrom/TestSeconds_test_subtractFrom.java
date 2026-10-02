package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime base = LocalTime.of(11, 30);

        // Subtracting zero seconds leaves the time unchanged
        LocalTime afterZero = (LocalTime) Seconds.of(0).subtractFrom(base);
        assertEquals(LocalTime.of(11, 30), afterZero);

        // Subtracting 6 seconds moves the time back by 6 seconds
        LocalTime afterSix = (LocalTime) Seconds.of(6).subtractFrom(base);
        assertEquals(LocalTime.of(11, 29, 54), afterSix);
    }
}
