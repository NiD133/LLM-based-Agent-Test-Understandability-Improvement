package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverChronology#prolepticYear} maps a Julian era
 * plus a year-of-era onto the correct proleptic year.
 * <p>
 * In the proleptic numbering, AD years run 1, 2, 3, ... while BC years count
 * backwards through and below zero: BC 1 is proleptic year 0, BC 2 is -1, and
 * so on.
 */
public class TestBritishCutoverChronology_test_prolepticYear_specific {

    private static final BritishCutoverChronology CHRONOLOGY = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_prolepticYear_specific() {
        // AD years map directly onto the proleptic year.
        assertEquals(4, CHRONOLOGY.prolepticYear(JulianEra.AD, 4));
        assertEquals(3, CHRONOLOGY.prolepticYear(JulianEra.AD, 3));
        assertEquals(2, CHRONOLOGY.prolepticYear(JulianEra.AD, 2));
        assertEquals(1, CHRONOLOGY.prolepticYear(JulianEra.AD, 1));

        // BC years count backwards: BC 1 -> 0, BC 2 -> -1, BC 3 -> -2, BC 4 -> -3.
        assertEquals(0, CHRONOLOGY.prolepticYear(JulianEra.BC, 1));
        assertEquals(-1, CHRONOLOGY.prolepticYear(JulianEra.BC, 2));
        assertEquals(-2, CHRONOLOGY.prolepticYear(JulianEra.BC, 3));
        assertEquals(-3, CHRONOLOGY.prolepticYear(JulianEra.BC, 4));
    }
}
