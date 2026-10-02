package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_from_LocalDate {

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(1, 1, 1, 0, 12, 31),
                sample(1, 1, 2, 1, 1, 1),
                sample(1, 1, 3, 1, 1, 2),
                sample(1, 1, 28, 1, 1, 27),
                sample(1, 2, 1, 1, 1, 28),
                sample(1, 2, 2, 1, 1, 29),
                sample(1, 2, 3, 1, 1, 30),

                sample(6, 13, 6, 6, 12, 1),
                sample(6, 13, 7, 6, 12, 2),
                sample(6, 14, 1, 6, 12, 3),
                sample(6, 14, 2, 6, 12, 4),
                sample(6, 14, 3, 6, 12, 5),
                sample(6, 14, 27, 6, 12, 29),
                sample(6, 14, 28, 6, 12, 30),
                sample(7, 1, 1, 6, 12, 31),
                sample(7, 1, 2, 7, 1, 1),

                sample(399, 13, 6, 399, 12, 3),
                sample(399, 13, 7, 399, 12, 4),
                sample(399, 14, 1, 399, 12, 5),
                sample(399, 14, 2, 399, 12, 6),
                sample(399, 14, 3, 399, 12, 7),
                sample(400, 13, 27, 400, 12, 29),
                sample(400, 13, 28, 400, 12, 30),
                sample(401, 1, 1, 400, 12, 31),
                sample(401, 1, 2, 401, 1, 1),
                sample(401, 1, 3, 401, 1, 2),

                sample(0, 13, 28, 0, 12, 30),
                sample(0, 13, 27, 0, 12, 29),
                sample(1582, 10, 5, 1582, 9, 9),
                sample(1582, 10, 6, 1582, 9, 10),
                sample(1945, 10, 28, 1945, 10, 6),
                sample(2012, 6, 23, 2012, 6, 4),
                sample(2012, 6, 24, 2012, 6, 5),

                sample(-6, 1, 1, -6, 1, 2),
                sample(-6, 13, 6, -6, 12, 9),
                sample(-6, 13, 7, -6, 12, 10),
                sample(-6, 14, 1, -6, 12, 11),
                sample(-6, 14, 2, -6, 12, 12),
                sample(-6, 14, 27, -5, 1, 6),
                sample(-6, 14, 28, -5, 1, 7),
                sample(-5, 1, 1, -5, 1, 8),
                sample(-5, 1, 2, -5, 1, 9),

                sample(-99, 1, 1, -99, 1, 6),
                sample(-99, 13, 6, -99, 12, 13),
                sample(-99, 13, 7, -99, 12, 14),
                sample(-99, 14, 1, -99, 12, 15),
                sample(-99, 14, 2, -99, 12, 16),
                sample(-100, 1, 1, -101, 12, 31),
                sample(-100, 13, 6, -100, 12, 7),
                sample(-100, 13, 7, -100, 12, 8),
                sample(-100, 14, 1, -100, 12, 9),
                sample(-100, 14, 2, -100, 12, 10)
        };
    }

    private static Object[] sample(
            int paxYear,
            int paxMonth,
            int paxDay,
            int isoYear,
            int isoMonth,
            int isoDay) {

        return new Object[] {
                PaxDate.of(paxYear, paxMonth, paxDay),
                LocalDate.of(isoYear, isoMonth, isoDay)
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_PaxDate_from_LocalDate(PaxDate pax, LocalDate iso) {
        assertEquals(pax, PaxDate.from(iso));
    }
}
