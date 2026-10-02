package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BritishCutoverChronology#prolepticYear(java.time.chrono.Era, int)}.
 *
 * The proleptic-year mapping rule:
 *   AD yearOfEra N  ->  proleptic year  N      (positive, counting forward)
 *   BC yearOfEra N  ->  proleptic year  1 - N  (0 for BC 1, then negative)
 */
public class TestBritishCutoverChronology_test_prolepticYear_specific {

    private static final BritishCutoverChronology CHRONO = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_prolepticYear_specific() {
        // AD years: prolepticYear == yearOfEra (no transformation)
        assertEquals(4, CHRONO.prolepticYear(JulianEra.AD, 4));
        assertEquals(3, CHRONO.prolepticYear(JulianEra.AD, 3));
        assertEquals(2, CHRONO.prolepticYear(JulianEra.AD, 2));
        assertEquals(1, CHRONO.prolepticYear(JulianEra.AD, 1));

        // BC years: prolepticYear == 1 - yearOfEra  (BC 1 → 0, BC 2 → -1, ...)
        assertEquals( 0, CHRONO.prolepticYear(JulianEra.BC, 1));
        assertEquals(-1, CHRONO.prolepticYear(JulianEra.BC, 2));
        assertEquals(-2, CHRONO.prolepticYear(JulianEra.BC, 3));
        assertEquals(-3, CHRONO.prolepticYear(JulianEra.BC, 4));
    }
}
