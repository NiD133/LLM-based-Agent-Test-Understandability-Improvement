package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSymmetry010Chronology_test_LocalDate_until_Symmetry010Date {

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(1, 1, 1, 1, 1, 1),
                sample(272, 2, 28, 272, 2, 27),
                sample(272, 2, 27, 272, 2, 26),
                sample(742, 3, 27, 742, 4, 2),
                sample(742, 4, 2, 742, 4, 7),
                sample(1066, 10, 14, 1066, 10, 14),
                sample(1304, 7, 21, 1304, 7, 20),
                sample(1304, 7, 20, 1304, 7, 19),
                sample(1433, 11, 12, 1433, 11, 10),
                sample(1433, 11, 10, 1433, 11, 8),
                sample(1452, 4, 11, 1452, 4, 15),
                sample(1452, 4, 15, 1452, 4, 19),
                sample(1492, 10, 10, 1492, 10, 12),
                sample(1492, 10, 12, 1492, 10, 14),
                sample(1564, 2, 18, 1564, 2, 15),
                sample(1564, 2, 15, 1564, 2, 12),
                sample(1564, 4, 28, 1564, 4, 26),
                sample(1564, 4, 26, 1564, 4, 24),
                sample(1643, 1, 7, 1643, 1, 4),
                sample(1643, 1, 4, 1643, 1, 1),
                sample(1707, 4, 12, 1707, 4, 15),
                sample(1707, 4, 15, 1707, 4, 18),
                sample(1789, 7, 16, 1789, 7, 14),
                sample(1789, 7, 14, 1789, 7, 12),
                sample(1879, 3, 14, 1879, 3, 14),
                sample(1941, 9, 11, 1941, 9, 9),
                sample(1941, 9, 9, 1941, 9, 7),
                sample(1970, 1, 4, 1970, 1, 1),
                sample(1970, 1, 1, 1969, 12, 29),
                sample(1999, 12, 29, 2000, 1, 1),
                sample(2000, 1, 1, 2000, 1, 3),
        };
    }

    private static Object[] sample(
            int symmetryYear,
            int symmetryMonth,
            int symmetryDay,
            int isoYear,
            int isoMonth,
            int isoDay) {

        return new Object[] {
                Symmetry010Date.of(symmetryYear, symmetryMonth, symmetryDay),
                LocalDate.of(isoYear, isoMonth, isoDay),
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_Symmetry010Date(Symmetry010Date sym010, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(sym010));
    }
}
