package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link PaxChronology#prolepticYear(java.time.chrono.Era, int)} for specific era/year pairs.
 * <p>
 * The proleptic year is derived from the era and the year-of-era:
 * <ul>
 *   <li>For the current era (CE) the proleptic year equals the year-of-era.</li>
 *   <li>For the previous era (BCE) the proleptic year is {@code 1 - yearOfEra},
 *       so it counts down through zero into negative values.</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_prolepticYear_specific {

    @Test
    public void prolepticYear_inCurrentEra_equalsYearOfEra() {
        assertEquals(4, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 4));
        assertEquals(3, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 3));
        assertEquals(2, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 2));
        assertEquals(1, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 1));
    }

    @Test
    public void prolepticYear_inPreviousEra_countsDownThroughZero() {
        assertEquals(0, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 1));
        assertEquals(-1, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 2));
        assertEquals(-2, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 3));
        assertEquals(-3, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 4));
    }
}
