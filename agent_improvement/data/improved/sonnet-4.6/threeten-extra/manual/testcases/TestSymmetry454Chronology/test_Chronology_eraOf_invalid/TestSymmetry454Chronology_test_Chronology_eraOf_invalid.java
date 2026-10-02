package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#eraOf(int)} rejects era values outside the valid range [0, 1].
 * The Symmetry454 calendar shares IsoEra's two eras (BCE=0, CE=1), so value 2 is invalid.
 */
public class TestSymmetry454Chronology_test_Chronology_eraOf_invalid {

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Era values for Symmetry454 are 0 (BCE) and 1 (CE); 2 is out of range
        assertThrows(DateTimeException.class, () -> Symmetry454Chronology.INSTANCE.eraOf(2));
    }
}
