package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_BritishCutoverDate_chronology_dateEpochDay {

    public static Stream<Arguments> data_samples() {
        return Stream.of(
                // The British calendar is two days ahead of ISO at the start of AD 1.
                Arguments.of(BritishCutoverDate.of(1, 1, 1), LocalDate.of(0, 12, 30)),
                Arguments.of(BritishCutoverDate.of(1, 1, 2), LocalDate.of(0, 12, 31)),
                Arguments.of(BritishCutoverDate.of(1, 1, 3), LocalDate.of(1, 1, 1)),
                Arguments.of(BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26)),
                Arguments.of(BritishCutoverDate.of(1, 3, 1), LocalDate.of(1, 2, 27)),
                Arguments.of(BritishCutoverDate.of(1, 3, 2), LocalDate.of(1, 2, 28)),
                Arguments.of(BritishCutoverDate.of(1, 3, 3), LocalDate.of(1, 3, 1)),

                // Julian leap-year behavior before the Gregorian cutover.
                Arguments.of(BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26)),
                Arguments.of(BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27)),
                Arguments.of(BritishCutoverDate.of(4, 3, 1), LocalDate.of(4, 2, 28)),
                Arguments.of(BritishCutoverDate.of(4, 3, 2), LocalDate.of(4, 2, 29)),
                Arguments.of(BritishCutoverDate.of(4, 3, 3), LocalDate.of(4, 3, 1)),
                Arguments.of(BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26)),
                Arguments.of(BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27)),
                Arguments.of(BritishCutoverDate.of(100, 3, 1), LocalDate.of(100, 2, 28)),
                Arguments.of(BritishCutoverDate.of(100, 3, 2), LocalDate.of(100, 3, 1)),
                Arguments.of(BritishCutoverDate.of(100, 3, 3), LocalDate.of(100, 3, 2)),
                Arguments.of(BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29)),
                Arguments.of(BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28)),

                // Dates around earlier Gregorian adoption remain Julian in the British chronology.
                Arguments.of(BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14)),
                Arguments.of(BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15)),

                // Boundary cases for Britain's 1752 cutover year.
                Arguments.of(BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31)),
                Arguments.of(BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11)),
                Arguments.of(BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12)),
                Arguments.of(BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12)),
                Arguments.of(BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13)),
                Arguments.of(BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14)),
                Arguments.of(BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24)),
                Arguments.of(BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14)),

                // Modern BritishCutover dates align exactly with ISO dates.
                Arguments.of(BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12)),
                Arguments.of(BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5)),
                Arguments.of(BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(BritishCutoverDate expectedCutoverDate, LocalDate isoDate) {
        assertEquals(expectedCutoverDate, BritishCutoverChronology.INSTANCE.dateEpochDay(isoDate.toEpochDay()));
    }
}
