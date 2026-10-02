package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BritishCutoverChronology#prolepticYear} rejects era types
 * other than {@link JulianEra}.  Passing an {@link IsoEra} must throw
 * {@link ClassCastException} because the chronology requires a {@code JulianEra}.
 */
public class TestBritishCutoverChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_badEra() {
        // IsoEra.CE is not a JulianEra, so prolepticYear must throw ClassCastException
        assertThrows(ClassCastException.class,
                () -> BritishCutoverChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
