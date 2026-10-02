package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedChronology#eraOf(int)} rejects an
 * invalid era value (the only valid value is 1, for the CE era).
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // 0 is not a valid era value, so eraOf must throw.
        assertThrows(DateTimeException.class, () -> InternationalFixedChronology.INSTANCE.eraOf(0));
    }
}
