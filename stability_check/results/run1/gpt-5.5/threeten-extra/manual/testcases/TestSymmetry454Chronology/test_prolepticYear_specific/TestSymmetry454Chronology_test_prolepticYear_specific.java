package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertCeYearOfEraMapsToSameProlepticYear(4);
        assertCeYearOfEraMapsToSameProlepticYear(3);
        assertCeYearOfEraMapsToSameProlepticYear(2);
        assertCeYearOfEraMapsToSameProlepticYear(1);
        assertCeYearOfEraMapsToSameProlepticYear(2000);
        assertCeYearOfEraMapsToSameProlepticYear(1582);
    }

    private static void assertCeYearOfEraMapsToSameProlepticYear(int yearOfEra) {
        assertEquals(
                yearOfEra,
                Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, yearOfEra));
    }
}
