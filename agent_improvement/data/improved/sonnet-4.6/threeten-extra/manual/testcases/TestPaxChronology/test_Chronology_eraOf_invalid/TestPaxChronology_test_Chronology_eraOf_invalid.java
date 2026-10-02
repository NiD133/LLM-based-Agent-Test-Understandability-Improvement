package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_eraOf_invalid {

    // The Pax calendar has exactly two eras: BCE (value 0) and CE (value 1).
    // Any value outside [0, 1] must be rejected with a DateTimeException.
    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> PaxChronology.INSTANCE.eraOf(2));
    }
}
