package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link Symmetry010Chronology#prolepticYear} returns the year-of-era unchanged
 * when called with {@link IsoEra#CE} and a positive year value.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_prolepticYear_specific {

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 1582, 2000 })
    public void test_prolepticYear_specific(int yearOfEra) {
        assertEquals(yearOfEra, Symmetry010Chronology.INSTANCE.prolepticYear(IsoEra.CE, yearOfEra));
    }
}
