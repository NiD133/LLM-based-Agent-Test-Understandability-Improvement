package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#prolepticYear(java.time.chrono.Era, int)}
 * rejects an era that is not a {@link JulianEra}.
 */
public class TestJulianChronology_test_prolepticYear_badEra {

    @Test
    public void test_prolepticYear_rejectsNonJulianEra() {
        // IsoEra.CE is an ISO era, not a JulianEra, so it must be rejected.
        assertThrows(ClassCastException.class,
                () -> JulianChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
