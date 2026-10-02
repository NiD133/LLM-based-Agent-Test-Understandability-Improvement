package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eraOf_invalid {

    /**
     * The International Fixed calendar only recognises era value 1 (CE).
     * Any other value (e.g. 0, -1, 2) must raise a DateTimeException.
     */
    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> InternationalFixedChronology.INSTANCE.eraOf(0));
    }
}
