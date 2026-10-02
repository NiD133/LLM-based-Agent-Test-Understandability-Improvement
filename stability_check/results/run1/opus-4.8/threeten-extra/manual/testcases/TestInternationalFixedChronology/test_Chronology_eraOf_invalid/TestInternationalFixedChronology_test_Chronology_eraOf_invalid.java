package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eraOf_invalid {

    /**
     * The International Fixed calendar defines a single valid era (CE, value 1),
     * so requesting the era for any other value must be rejected.
     */
    @Test
    public void eraOf_rejectsValueWithNoMatchingEra() {
        int invalidEraValue = 0;

        assertThrows(
                DateTimeException.class,
                () -> InternationalFixedChronology.INSTANCE.eraOf(invalidEraValue));
    }
}
