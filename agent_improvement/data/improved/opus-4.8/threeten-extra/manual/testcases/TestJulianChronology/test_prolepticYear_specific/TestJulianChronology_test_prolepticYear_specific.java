package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link JulianChronology#prolepticYear(java.time.chrono.Era, int)} for specific
 * era / year-of-era combinations.
 * <p>
 * The proleptic year maps onto the year-of-era as follows:
 * <ul>
 * <li>AD: the proleptic year equals the year-of-era (year N stays N).</li>
 * <li>BC: the proleptic year counts down, so year-of-era N becomes {@code 1 - N}
 *     (BC 1 is proleptic year 0, BC 2 is -1, and so on).</li>
 * </ul>
 */
public class TestJulianChronology_test_prolepticYear_specific {

    @Test
    public void prolepticYear_inAD_equalsYearOfEra() {
        assertEquals(4, JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 4));
        assertEquals(3, JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 3));
        assertEquals(2, JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 2));
        assertEquals(1, JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 1));
    }

    @Test
    public void prolepticYear_inBC_countsDownFromZero() {
        assertEquals(0, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 1));
        assertEquals(-1, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 2));
        assertEquals(-2, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 3));
        assertEquals(-3, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 4));
    }
}
