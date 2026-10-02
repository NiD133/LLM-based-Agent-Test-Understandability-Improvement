package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestDays_test_get {

    @Test
    public void test_get() {
        int amount = 6;

        assertEquals(amount, Days.of(amount).get(ChronoUnit.DAYS));
    }
}
