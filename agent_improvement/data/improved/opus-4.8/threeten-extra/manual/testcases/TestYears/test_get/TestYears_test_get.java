package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#get(java.time.temporal.TemporalUnit)}.
 */
public class TestYears_test_get {

    @Test
    public void get_returnsAmount_forYearsUnit() {
        Years sixYears = Years.of(6);

        long actualYears = sixYears.get(ChronoUnit.YEARS);

        assertEquals(6, actualYears);
    }
}
