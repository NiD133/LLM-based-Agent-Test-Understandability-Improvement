package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_toEpochDay {

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(PaxDate.of(1, 1, 1), LocalDate.of(0, 12, 31)),
                sample(PaxDate.of(1, 1, 2), LocalDate.of(1, 1, 1)),
                sample(PaxDate.of(1, 1, 3), LocalDate.of(1, 1, 2)),
                sample(PaxDate.of(1, 1, 28), LocalDate.of(1, 1, 27)),
                sample(PaxDate.of(1, 2, 1), LocalDate.of(1, 1, 28)),
                sample(PaxDate.of(1, 2, 2), LocalDate.of(1, 1, 29)),
                sample(PaxDate.of(1, 2, 3), LocalDate.of(1, 1, 30)),

                sample(PaxDate.of(6, 13, 6), LocalDate.of(6, 12, 1)),
                sample(PaxDate.of(6, 13, 7), LocalDate.of(6, 12, 2)),
                sample(PaxDate.of(6, 14, 1), LocalDate.of(6, 12, 3)),
                sample(PaxDate.of(6, 14, 2), LocalDate.of(6, 12, 4)),
                sample(PaxDate.of(6, 14, 3), LocalDate.of(6, 12, 5)),
                sample(PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29)),
                sample(PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30)),
                sample(PaxDate.of(7, 1, 1), LocalDate.of(6, 12, 31)),
                sample(PaxDate.of(7, 1, 2), LocalDate.of(7, 1, 1)),

                sample(PaxDate.of(399, 13, 6), LocalDate.of(399, 12, 3)),
                sample(PaxDate.of(399, 13, 7), LocalDate.of(399, 12, 4)),
                sample(PaxDate.of(399, 14, 1), LocalDate.of(399, 12, 5)),
                sample(PaxDate.of(399, 14, 2), LocalDate.of(399, 12, 6)),
                sample(PaxDate.of(399, 14, 3), LocalDate.of(399, 12, 7)),
                sample(PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29)),
                sample(PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30)),
                sample(PaxDate.of(401, 1, 1), LocalDate.of(400, 12, 31)),
                sample(PaxDate.of(401, 1, 2), LocalDate.of(401, 1, 1)),
                sample(PaxDate.of(401, 1, 3), LocalDate.of(401, 1, 2)),

                sample(PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30)),
                sample(PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29)),
                sample(PaxDate.of(1582, 10, 5), LocalDate.of(1582, 9, 9)),
                sample(PaxDate.of(1582, 10, 6), LocalDate.of(1582, 9, 10)),
                sample(PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10, 6)),
                sample(PaxDate.of(2012, 6, 23), LocalDate.of(2012, 6, 4)),
                sample(PaxDate.of(2012, 6, 24), LocalDate.of(2012, 6, 5)),

                sample(PaxDate.of(-6, 1, 1), LocalDate.of(-6, 1, 2)),
                sample(PaxDate.of(-6, 13, 6), LocalDate.of(-6, 12, 9)),
                sample(PaxDate.of(-6, 13, 7), LocalDate.of(-6, 12, 10)),
                sample(PaxDate.of(-6, 14, 1), LocalDate.of(-6, 12, 11)),
                sample(PaxDate.of(-6, 14, 2), LocalDate.of(-6, 12, 12)),
                sample(PaxDate.of(-6, 14, 27), LocalDate.of(-5, 1, 6)),
                sample(PaxDate.of(-6, 14, 28), LocalDate.of(-5, 1, 7)),
                sample(PaxDate.of(-5, 1, 1), LocalDate.of(-5, 1, 8)),
                sample(PaxDate.of(-5, 1, 2), LocalDate.of(-5, 1, 9)),

                sample(PaxDate.of(-99, 1, 1), LocalDate.of(-99, 1, 6)),
                sample(PaxDate.of(-99, 13, 6), LocalDate.of(-99, 12, 13)),
                sample(PaxDate.of(-99, 13, 7), LocalDate.of(-99, 12, 14)),
                sample(PaxDate.of(-99, 14, 1), LocalDate.of(-99, 12, 15)),
                sample(PaxDate.of(-99, 14, 2), LocalDate.of(-99, 12, 16)),
                sample(PaxDate.of(-100, 1, 1), LocalDate.of(-101, 12, 31)),
                sample(PaxDate.of(-100, 13, 6), LocalDate.of(-100, 12, 7)),
                sample(PaxDate.of(-100, 13, 7), LocalDate.of(-100, 12, 8)),
                sample(PaxDate.of(-100, 14, 1), LocalDate.of(-100, 12, 9)),
                sample(PaxDate.of(-100, 14, 2), LocalDate.of(-100, 12, 10)),
        };
    }

    private static Object[] sample(PaxDate paxDate, LocalDate isoDate) {
        return new Object[] { paxDate, isoDate };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_PaxDate_toEpochDay(PaxDate pax, LocalDate iso) {
        assertEquals(iso.toEpochDay(), pax.toEpochDay());
    }
}
