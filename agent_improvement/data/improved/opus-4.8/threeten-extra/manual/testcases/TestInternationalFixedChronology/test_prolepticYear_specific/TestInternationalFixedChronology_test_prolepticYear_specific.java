package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedChronology#prolepticYear} returns the
 * year-of-era unchanged for the (only) CE era.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        // For the CE era the proleptic year is identical to the year-of-era.
        assertEquals(4, prolepticYearOfCe(4));
        assertEquals(3, prolepticYearOfCe(3));
        assertEquals(2, prolepticYearOfCe(2));
        assertEquals(1, prolepticYearOfCe(1));
        assertEquals(2000, prolepticYearOfCe(2000));
        assertEquals(1582, prolepticYearOfCe(1582));
    }

    private static int prolepticYearOfCe(int yearOfEra) {
        return InternationalFixedChronology.INSTANCE.prolepticYear(InternationalFixedEra.CE, yearOfEra);
    }
}
