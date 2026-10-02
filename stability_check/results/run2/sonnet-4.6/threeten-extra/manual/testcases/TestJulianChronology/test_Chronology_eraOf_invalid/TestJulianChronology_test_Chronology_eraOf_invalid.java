package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_Chronology_eraOf_invalid {

    // Julian calendar has only two valid era values: 0 (BC) and 1 (AD).
    // Any other value must throw DateTimeException.
    @Test
    public void test_Chronology_eraOf_invalid() {
        int invalidEraValue = 2;
        assertThrows(DateTimeException.class, () -> JulianChronology.INSTANCE.eraOf(invalidEraValue));
    }
}
