package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_prolepticYear_specific {

    /**
     * Verifies that prolepticYear(CE, yearOfEra) == yearOfEra for representative CE years.
     * In the International Fixed calendar there is only one era (CE), so the proleptic year
     * is identical to the year-of-era value.
     */
    @Test
    public void test_prolepticYear_specific() {
        InternationalFixedChronology chrono = InternationalFixedChronology.INSTANCE;

        assertEquals(1,    chrono.prolepticYear(InternationalFixedEra.CE, 1));
        assertEquals(2,    chrono.prolepticYear(InternationalFixedEra.CE, 2));
        assertEquals(3,    chrono.prolepticYear(InternationalFixedEra.CE, 3));
        assertEquals(4,    chrono.prolepticYear(InternationalFixedEra.CE, 4));
        assertEquals(1582, chrono.prolepticYear(InternationalFixedEra.CE, 1582));
        assertEquals(2000, chrono.prolepticYear(InternationalFixedEra.CE, 2000));
    }
}
