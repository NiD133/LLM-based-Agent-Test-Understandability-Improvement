package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // The Julian calendar only has two valid eras: 0 (BC) and 1 (AD).
        // Passing 2 should throw DateTimeException.
        assertThrows(DateTimeException.class, () -> JulianChronology.INSTANCE.eraOf(2));
    }
}
