package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverChronology#prolepticYear} rejects an era that
 * does not belong to this chronology.
 */
public class TestBritishCutoverChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_badEra() {
        // BritishCutoverChronology expects a JulianEra; passing an IsoEra must be rejected.
        assertThrows(
                ClassCastException.class,
                () -> BritishCutoverChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
