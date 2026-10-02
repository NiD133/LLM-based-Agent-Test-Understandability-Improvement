package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#eraOf(int)} rejects an era value
 * that does not correspond to a valid {@code IsoEra}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // The Symmetry454 chronology only recognises the ISO eras (0 = BCE, 1 = CE);
        // any other value, such as 2, must be rejected.
        assertThrows(DateTimeException.class, () -> Symmetry454Chronology.INSTANCE.eraOf(2));
    }
}
