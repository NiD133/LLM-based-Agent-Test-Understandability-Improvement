package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestHours_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime base = LocalTime.of(11, 30);

        // Subtracting zero hours leaves the time unchanged
        assertEquals(LocalTime.of(11, 30), Hours.of(0).subtractFrom(base));

        // Subtracting 6 hours from 11:30 yields 05:30
        assertEquals(LocalTime.of(5, 30), Hours.of(6).subtractFrom(base));
    }
}
