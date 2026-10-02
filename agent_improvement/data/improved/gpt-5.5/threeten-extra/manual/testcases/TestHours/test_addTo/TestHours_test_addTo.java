package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestHours_test_addTo {

    @Test
    public void test_addTo() {
        LocalTime baseTime = LocalTime.of(11, 30);

        assertEquals(LocalTime.of(11, 30), Hours.of(0).addTo(baseTime));
        assertEquals(LocalTime.of(17, 30), Hours.of(6).addTo(baseTime));
    }
}
