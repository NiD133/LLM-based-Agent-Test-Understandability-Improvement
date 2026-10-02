package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime baseTime = LocalTime.of(11, 30);

        // Subtracting zero minutes leaves the time unchanged
        LocalTime resultZero = (LocalTime) Minutes.of(0).subtractFrom(baseTime);
        assertEquals(LocalTime.of(11, 30), resultZero);

        // Subtracting 6 minutes moves the time back by 6 minutes
        LocalTime resultSix = (LocalTime) Minutes.of(6).subtractFrom(baseTime);
        assertEquals(LocalTime.of(11, 24), resultSix);
    }
}
