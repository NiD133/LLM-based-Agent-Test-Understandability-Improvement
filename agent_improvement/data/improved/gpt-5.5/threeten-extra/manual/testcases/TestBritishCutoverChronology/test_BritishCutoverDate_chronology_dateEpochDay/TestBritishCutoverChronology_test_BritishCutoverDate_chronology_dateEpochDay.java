package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_BritishCutoverDate_chronology_dateEpochDay {

    public static Object[][] data_samples() {
        return new Object[][] {
            // Earliest supported dates use Julian calendar alignment.
            sample(BritishCutoverDate.of(1, 1, 1), LocalDate.of(0, 12, 30)),
            sample(BritishCutoverDate.of(1, 1, 2), LocalDate.of(0, 12, 31)),
            sample(BritishCutoverDate.of(1, 1, 3), LocalDate.of(1, 1, 1)),
            sample(BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26)),
            sample(BritishCutoverDate.of(1, 3, 1), LocalDate.of(1, 2, 27)),
            sample(BritishCutoverDate.of(1, 3, 2), LocalDate.of(1, 2, 28)),
            sample(BritishCutoverDate.of(1, 3, 3), LocalDate.of(1, 3, 1)),
            sample(BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26)),
            sample(BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27)),
            sample(BritishCutoverDate.of(4, 3, 1), LocalDate.of(4, 2, 28)),
            sample(BritishCutoverDate.of(4, 3, 2), LocalDate.of(4, 2, 29)),
            sample(BritishCutoverDate.of(4, 3, 3), LocalDate.of(4, 3, 1)),
            sample(BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26)),
            sample(BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27)),
            sample(BritishCutoverDate.of(100, 3, 1), LocalDate.of(100, 2, 28)),
            sample(BritishCutoverDate.of(100, 3, 2), LocalDate.of(100, 3, 1)),
            sample(BritishCutoverDate.of(100, 3, 3), LocalDate.of(100, 3, 2)),
            sample(BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29)),
            sample(BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28)),

            // Dates before the British cutover still preserve their Julian offset.
            sample(BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14)),
            sample(BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15)),
            sample(BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31)),
            sample(BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11)),
            sample(BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12)),
            sample(BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12)),
            sample(BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13)),

            // Gap dates are accepted leniently and converted from their Julian mapping.
            sample(BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14)),
            sample(BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24)),

            // Dates from the cutover day onward match ISO epoch-day dates directly.
            sample(BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14)),
            sample(BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12)),
            sample(BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5)),
            sample(BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6)),
        };
    }

    private static Object[] sample(BritishCutoverDate expectedCutoverDate, LocalDate isoDate) {
        return new Object[] { expectedCutoverDate, isoDate };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(
            BritishCutoverDate expectedCutoverDate,
            LocalDate isoDate) {

        assertEquals(
                expectedCutoverDate,
                BritishCutoverChronology.INSTANCE.dateEpochDay(isoDate.toEpochDay()));
    }
}
