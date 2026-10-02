package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestDays_test_get {

    @Test
    public void test_get() {
        // Days.get(ChronoUnit.DAYS) must return the same value passed to Days.of()
        assertEquals(6, Days.of(6).get(ChronoUnit.DAYS));
    }
}
