package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_from_Symmetry010Date {

    public static Object[][] data_samples() {
        return new Object[][] {
                { Symmetry010Date.of(1, 1, 1), LocalDate.of(1, 1, 1) },
                { Symmetry010Date.of(272, 2, 28), LocalDate.of(272, 2, 27) },
                { Symmetry010Date.of(272, 2, 27), LocalDate.of(272, 2, 26) },
                { Symmetry010Date.of(742, 3, 27), LocalDate.of(742, 4, 2) },
                { Symmetry010Date.of(742, 4, 2), LocalDate.of(742, 4, 7) },
                { Symmetry010Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },
                { Symmetry010Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20) },
                { Symmetry010Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19) },
                { Symmetry010Date.of(1433, 11, 12), LocalDate.of(1433, 11, 10) },
                { Symmetry010Date.of(1433, 11, 10), LocalDate.of(1433, 11, 8) },
                { Symmetry010Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15) },
                { Symmetry010Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19) },
                { Symmetry010Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
                { Symmetry010Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },
                { Symmetry010Date.of(1564, 2, 18), LocalDate.of(1564, 2, 15) },
                { Symmetry010Date.of(1564, 2, 15), LocalDate.of(1564, 2, 12) },
                { Symmetry010Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26) },
                { Symmetry010Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24) },
                { Symmetry010Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4) },
                { Symmetry010Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1) },
                { Symmetry010Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15) },
                { Symmetry010Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18) },
                { Symmetry010Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14) },
                { Symmetry010Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12) },
                { Symmetry010Date.of(1879, 3, 14), LocalDate.of(1879, 3, 14) },
                { Symmetry010Date.of(1941, 9, 11), LocalDate.of(1941, 9, 9) },
                { Symmetry010Date.of(1941, 9, 9), LocalDate.of(1941, 9, 7) },
                { Symmetry010Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1) },
                { Symmetry010Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29) },
                { Symmetry010Date.of(1999, 12, 29), LocalDate.of(2000, 1, 1) },
                { Symmetry010Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3) }
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_Symmetry010Date(Symmetry010Date sym010, LocalDate iso) {
        assertEquals(iso, LocalDate.from(sym010));
    }
}
