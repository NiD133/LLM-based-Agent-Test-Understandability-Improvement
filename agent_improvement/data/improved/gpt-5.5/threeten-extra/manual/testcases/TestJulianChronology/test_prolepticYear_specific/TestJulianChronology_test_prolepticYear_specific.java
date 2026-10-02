package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertProlepticYear(4, JulianEra.AD, 4);
        assertProlepticYear(3, JulianEra.AD, 3);
        assertProlepticYear(2, JulianEra.AD, 2);
        assertProlepticYear(1, JulianEra.AD, 1);

        assertProlepticYear(0, JulianEra.BC, 1);
        assertProlepticYear(-1, JulianEra.BC, 2);
        assertProlepticYear(-2, JulianEra.BC, 3);
        assertProlepticYear(-3, JulianEra.BC, 4);
    }

    private static void assertProlepticYear(int expectedProlepticYear, JulianEra era, int yearOfEra) {
        assertEquals(expectedProlepticYear, JulianChronology.INSTANCE.prolepticYear(era, yearOfEra));
    }
}
