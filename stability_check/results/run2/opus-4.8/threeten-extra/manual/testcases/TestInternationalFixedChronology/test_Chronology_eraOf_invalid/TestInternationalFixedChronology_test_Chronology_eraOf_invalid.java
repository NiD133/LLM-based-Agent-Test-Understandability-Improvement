package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedChronology#eraOf(int)} rejects an
 * out-of-range era value.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eraOf_invalid {

    @Test
    public void eraOf_withInvalidValue_throwsDateTimeException() {
        // 0 is not a valid era value for the International Fixed calendar.
        int invalidEraValue = 0;

        assertThrows(DateTimeException.class,
                () -> InternationalFixedChronology.INSTANCE.eraOf(invalidEraValue));
    }
}
