package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link PaxChronology#eraOf(int)} rejects values that do not
 * correspond to a valid Pax era.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_eraOf_invalid {

    @Test
    public void eraOf_rejectsValueOutsideEraRange() {
        // The Pax calendar defines only eras 0 (BCE) and 1 (CE); 2 is invalid.
        int invalidEraValue = 2;

        assertThrows(DateTimeException.class, () -> PaxChronology.INSTANCE.eraOf(invalidEraValue));
    }
}
