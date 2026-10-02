package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestHours_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime base = LocalTime.of(11, 30);

        assertEquals(
                LocalTime.of(11, 30),
                Hours.of(0).subtractFrom(base));
        assertEquals(
                LocalTime.of(5, 30),
                Hours.of(6).subtractFrom(base));
    }
}
