package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertCeYearMapsToSameProlepticYear(4);
        assertCeYearMapsToSameProlepticYear(3);
        assertCeYearMapsToSameProlepticYear(2);
        assertCeYearMapsToSameProlepticYear(1);
        assertCeYearMapsToSameProlepticYear(2000);
        assertCeYearMapsToSameProlepticYear(1582);
    }

    private static void assertCeYearMapsToSameProlepticYear(int yearOfEra) {
        assertEquals(yearOfEra, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, yearOfEra));
    }
}
