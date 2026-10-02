package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_prolepticYear_specific {

    /**
     * Verifies the proleptic year mapping for both Pax eras:
     *   CE year N  -> proleptic year N  (1, 2, 3, 4, ...)
     *   BCE year N -> proleptic year 1-N (0, -1, -2, -3, ...)
     */
    @Test
    public void test_prolepticYear_specific() {
        // CE years map directly to positive proleptic years
        assertEquals(4, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 4));
        assertEquals(3, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 3));
        assertEquals(2, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 2));
        assertEquals(1, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 1));

        // BCE years map to zero and negative proleptic years (BCE 1 = year 0, BCE 2 = year -1, ...)
        assertEquals(0,  PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 1));
        assertEquals(-1, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 2));
        assertEquals(-2, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 3));
        assertEquals(-3, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 4));
    }
}
