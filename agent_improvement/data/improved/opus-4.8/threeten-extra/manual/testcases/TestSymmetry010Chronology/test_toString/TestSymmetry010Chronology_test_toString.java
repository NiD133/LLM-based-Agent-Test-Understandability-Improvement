package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the textual representation produced by {@link Symmetry010Date#toString()}.
 * <p>
 * The expected format is {@code "Sym010 <era> <year>/<month>/<day>"}, where the
 * month and day are always zero-padded to two digits while the year is not padded.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_toString {

    @Test
    public void toString_formatsDateWithChronologyEraAndPaddedFields() {
        assertEquals("Sym010 CE 1/01/01", Symmetry010Date.of(1, 1, 1).toString());
        assertEquals("Sym010 CE 1970/02/31", Symmetry010Date.of(1970, 2, 31).toString());
        assertEquals("Sym010 CE 2000/08/31", Symmetry010Date.of(2000, 8, 31).toString());
        assertEquals("Sym010 CE 2009/12/37", Symmetry010Date.of(2009, 12, 37).toString());
    }
}
