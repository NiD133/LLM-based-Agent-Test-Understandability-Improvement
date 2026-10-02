package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalTime baseTime = LocalTime.of(11, 30);

        assertEquals(LocalTime.of(11, 30), Minutes.of(0).subtractFrom(baseTime));
        assertEquals(LocalTime.of(11, 24), Minutes.of(6).subtractFrom(baseTime));
    }
}
