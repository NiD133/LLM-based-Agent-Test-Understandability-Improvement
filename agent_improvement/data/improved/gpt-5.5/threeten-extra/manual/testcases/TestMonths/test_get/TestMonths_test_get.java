package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestMonths_test_get {

    @Test
    public void test_get() {
        assertEquals(6, Months.of(6).get(ChronoUnit.MONTHS));
    }
}
