package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertProlepticYear(4, IsoEra.CE, 4);
        assertProlepticYear(3, IsoEra.CE, 3);
        assertProlepticYear(2, IsoEra.CE, 2);
        assertProlepticYear(1, IsoEra.CE, 1);
        assertProlepticYear(2000, IsoEra.CE, 2000);
        assertProlepticYear(1582, IsoEra.CE, 1582);
    }

    private static void assertProlepticYear(int expectedProlepticYear, IsoEra era, int yearOfEra) {
        assertEquals(
                expectedProlepticYear,
                Symmetry010Chronology.INSTANCE.prolepticYear(era, yearOfEra));
    }
}
