package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverChronology#eraOf(int)} rejects an era value
 * that does not correspond to a valid {@link JulianEra}.
 */
public class TestBritishCutoverChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // The British cutover chronology only defines eras BC (0) and AD (1);
        // any other value must be rejected.
        assertThrows(DateTimeException.class, () -> BritishCutoverChronology.INSTANCE.eraOf(2));
    }
}
