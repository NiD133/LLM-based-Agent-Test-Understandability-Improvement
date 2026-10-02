package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that InternationalFixedChronology.eraOf() rejects invalid era values.
 * The only valid era value is 1 (CE); all others must throw DateTimeException.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> InternationalFixedChronology.INSTANCE.eraOf(0));
    }
}
