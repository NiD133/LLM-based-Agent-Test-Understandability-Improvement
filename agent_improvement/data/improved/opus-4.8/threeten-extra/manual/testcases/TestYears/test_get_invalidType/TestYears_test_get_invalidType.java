package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#get(java.time.temporal.TemporalUnit)} rejects an
 * unsupported temporal unit by throwing a {@link DateTimeException}.
 */
public class TestYears_test_get_invalidType {

    @Test
    public void get_withUnsupportedUnit_throwsException() {
        Years sixYears = Years.of(6);

        // QUARTER_YEARS is not the supported YEARS unit, so it must be rejected.
        assertThrows(DateTimeException.class, () -> sixYears.get(IsoFields.QUARTER_YEARS));
    }
}
