package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry010Chronology#eraOf(int)} rejects era values that
 * do not correspond to a valid ISO era.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // The only valid ISO era values are 0 (BCE) and 1 (CE);
        // requesting era 2 must fail.
        int invalidEraValue = 2;

        assertThrows(DateTimeException.class,
                () -> Symmetry010Chronology.INSTANCE.eraOf(invalidEraValue));
    }
}
