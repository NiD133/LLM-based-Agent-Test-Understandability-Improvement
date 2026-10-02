package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_addTo {

    @Test
    public void test_addTo() {
        LocalTime base = LocalTime.of(11, 30);

        // Adding zero seconds leaves the time unchanged
        assertEquals(LocalTime.of(11, 30), Seconds.of(0).addTo(base));

        // Adding a positive number of seconds advances the time accordingly
        assertEquals(LocalTime.of(11, 30, 6), Seconds.of(6).addTo(base));
    }
}
