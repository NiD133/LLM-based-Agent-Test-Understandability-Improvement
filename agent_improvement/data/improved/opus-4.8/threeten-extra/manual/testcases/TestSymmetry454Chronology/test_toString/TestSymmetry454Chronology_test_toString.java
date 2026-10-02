package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the human-readable {@link Symmetry454Date#toString()} representation.
 * <p>
 * The expected format is {@code "Sym454 <era> <year>/<month>/<day>"}, where the
 * month and day are zero-padded to two digits while the year is not padded.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_toString {

    /**
     * Each case pairs a Symmetry454 date with its expected {@code toString()} value,
     * covering single-digit years, the 35-day "long" months, and December in a leap year.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            { Symmetry454Date.of(1, 1, 1),      "Sym454 CE 1/01/01" },
            { Symmetry454Date.of(1970, 2, 35),  "Sym454 CE 1970/02/35" },
            { Symmetry454Date.of(2000, 8, 35),  "Sym454 CE 2000/08/35" },
            { Symmetry454Date.of(1970, 12, 35), "Sym454 CE 1970/12/35" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(Symmetry454Date date, String expected) {
        assertEquals(expected, date.toString());
    }
}
