package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_prolepticYear_specific {

    // AD yearOfEra maps directly to prolepticYear (1 → 1, 2 → 2, …)
    // BC yearOfEra maps as 1 - yearOfEra (BC 1 → 0, BC 2 → -1, …)
    @Test
    public void test_prolepticYear_specific() {
        assertEquals(4,  JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 4));
        assertEquals(3,  JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 3));
        assertEquals(2,  JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 2));
        assertEquals(1,  JulianChronology.INSTANCE.prolepticYear(JulianEra.AD, 1));
        assertEquals(0,  JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 1));
        assertEquals(-1, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 2));
        assertEquals(-2, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 3));
        assertEquals(-3, JulianChronology.INSTANCE.prolepticYear(JulianEra.BC, 4));
    }
}
