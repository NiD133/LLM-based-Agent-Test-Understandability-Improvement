package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#get(java.time.temporal.TemporalUnit)} rejects any
 * temporal unit other than months.
 */
public class TestMonths_test_get_invalidType {

    @Test
    public void get_withUnsupportedUnit_throwsDateTimeException() {
        Months sixMonths = Months.of(6);

        // QUARTER_YEARS is not the MONTHS unit, so querying it is unsupported.
        assertThrows(DateTimeException.class, () -> sixMonths.get(IsoFields.QUARTER_YEARS));
    }
}
