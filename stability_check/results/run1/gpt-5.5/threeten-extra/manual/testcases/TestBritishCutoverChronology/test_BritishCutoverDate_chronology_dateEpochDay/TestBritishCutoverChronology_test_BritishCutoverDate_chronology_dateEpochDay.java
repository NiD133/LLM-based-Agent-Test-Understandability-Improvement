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
                // Early proleptic Julian dates around leap-year boundaries.
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

                // Gregorian reform dates before Britain adopted the cutover.
                sample(BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14)),
                sample(BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15)),

                // British cutover from 1751/1752, including lenient gap dates.
                sample(BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31)),
                sample(BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11)),
                sample(BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12)),
                sample(BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12)),
                sample(BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13)),
                sample(BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14)),
                sample(BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24)),
                sample(BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14)),

                // Modern dates where British cutover and ISO dates are aligned.
                sample(BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12)),
                sample(BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5)),
                sample(BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6)));
    }

    private static Arguments sample(BritishCutoverDate cutoverDate, LocalDate isoDate) {
        return Arguments.of(cutoverDate, isoDate);
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(BritishCutoverDate cutoverDate, LocalDate isoDate) {
        BritishCutoverDate convertedDate = BritishCutoverChronology.INSTANCE.dateEpochDay(isoDate.toEpochDay());

        assertEquals(cutoverDate, convertedDate);
    }
}
