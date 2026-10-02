package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that JulianChronology.eraOf() rejects era values outside the valid range [0, 1].
 * The Julian calendar has exactly two eras: BC (0) and AD (1).
 */
public class TestJulianChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Era value 2 is out of range; Julian only supports BC=0 and AD=1
        assertThrows(DateTimeException.class, () -> JulianChronology.INSTANCE.eraOf(2));
    }
}
