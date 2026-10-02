package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#eraOf(int)} rejects era values that do not
 * correspond to one of the two Julian eras (BC = 0, AD = 1).
 */
public class TestJulianChronology_test_Chronology_eraOf_invalid {

    @Test
    public void eraOf_rejectsOutOfRangeEraValue() {
        // 2 is not a valid Julian era value, so a DateTimeException is expected.
        assertThrows(DateTimeException.class, () -> JulianChronology.INSTANCE.eraOf(2));
    }
}
