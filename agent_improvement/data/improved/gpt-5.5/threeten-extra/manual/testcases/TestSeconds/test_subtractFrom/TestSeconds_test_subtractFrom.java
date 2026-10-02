package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime baseTime = LocalTime.of(11, 30);

        assertEquals(LocalTime.of(11, 30), Seconds.of(0).subtractFrom(baseTime));
        assertEquals(LocalTime.of(11, 29, 54), Seconds.of(6).subtractFrom(baseTime));
    }
}
