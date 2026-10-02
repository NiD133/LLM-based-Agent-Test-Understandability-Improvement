package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestYears_test_get {

    @Test
    public void test_get() {
        Years sixYears = Years.of(6);

        assertEquals(6, sixYears.get(ChronoUnit.YEARS));
    }
}
