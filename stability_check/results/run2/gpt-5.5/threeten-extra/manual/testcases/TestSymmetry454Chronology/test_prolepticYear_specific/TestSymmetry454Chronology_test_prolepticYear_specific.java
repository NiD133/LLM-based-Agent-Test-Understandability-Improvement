package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertCommonEraYearOfEraIsProlepticYear(4);
        assertCommonEraYearOfEraIsProlepticYear(3);
        assertCommonEraYearOfEraIsProlepticYear(2);
        assertCommonEraYearOfEraIsProlepticYear(1);
        assertCommonEraYearOfEraIsProlepticYear(2000);
        assertCommonEraYearOfEraIsProlepticYear(1582);
    }

    private static void assertCommonEraYearOfEraIsProlepticYear(int yearOfEra) {
        assertEquals(yearOfEra, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, yearOfEra));
    }
}
