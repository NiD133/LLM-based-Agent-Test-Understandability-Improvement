package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestYears_test_get {

    @Test
    public void test_get() {
        // get(ChronoUnit.YEARS) must return the same value the instance was created with
        Years sixYears = Years.of(6);
        long retrievedYears = sixYears.get(ChronoUnit.YEARS);
        assertEquals(6, retrievedYears);
    }
}
