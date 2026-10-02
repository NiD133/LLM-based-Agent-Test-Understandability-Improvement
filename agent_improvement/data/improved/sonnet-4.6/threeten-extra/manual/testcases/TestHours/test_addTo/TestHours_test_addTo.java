package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestHours_test_addTo {

    @Test
    public void test_addTo() {
        LocalTime base = LocalTime.of(11, 30);

        // Adding zero hours leaves the time unchanged
        LocalTime afterZeroHours = (LocalTime) Hours.of(0).addTo(base);
        assertEquals(LocalTime.of(11, 30), afterZeroHours);

        // Adding six hours advances the time by six hours
        LocalTime afterSixHours = (LocalTime) Hours.of(6).addTo(base);
        assertEquals(LocalTime.of(17, 30), afterSixHours);
    }
}
