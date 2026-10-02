package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

/**
 * Verifies {@link PaxDate#from(java.time.temporal.TemporalAccessor)} when converting
 * an ISO {@link LocalDate} into the equivalent Pax-calendar date.
 * <p>
 * Each test case pairs a Pax date with the ISO date that denotes the same point on
 * the time-line, and asserts that converting the ISO date yields the Pax date.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_from_LocalDate {

    /**
     * Equivalent (Pax date, ISO date) pairs covering the start of the era, leap-year
     * boundaries (the inserted one-week 'Pax' month), 400-year cycle edges, and BCE years.
     */
    public static Stream<Arguments> data_samples() {
        return Stream.of(
            // Pax epoch and the first few days of year 1
            Arguments.of(PaxDate.of(1, 1, 1), LocalDate.of(0, 12, 31)),
            Arguments.of(PaxDate.of(1, 1, 2), LocalDate.of(1, 1, 1)),
            Arguments.of(PaxDate.of(1, 1, 3), LocalDate.of(1, 1, 2)),
            Arguments.of(PaxDate.of(1, 1, 28), LocalDate.of(1, 1, 27)),
            Arguments.of(PaxDate.of(1, 2, 1), LocalDate.of(1, 1, 28)),
            Arguments.of(PaxDate.of(1, 2, 2), LocalDate.of(1, 1, 29)),
            Arguments.of(PaxDate.of(1, 2, 3), LocalDate.of(1, 1, 30)),

            // Year 6 is a leap year: month 13 ('Pax') is inserted, shifting the old 13 to 14
            Arguments.of(PaxDate.of(6, 13, 6), LocalDate.of(6, 12, 1)),
            Arguments.of(PaxDate.of(6, 13, 7), LocalDate.of(6, 12, 2)),
            Arguments.of(PaxDate.of(6, 14, 1), LocalDate.of(6, 12, 3)),
            Arguments.of(PaxDate.of(6, 14, 2), LocalDate.of(6, 12, 4)),
            Arguments.of(PaxDate.of(6, 14, 3), LocalDate.of(6, 12, 5)),
            Arguments.of(PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29)),
            Arguments.of(PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30)),
            Arguments.of(PaxDate.of(7, 1, 1), LocalDate.of(6, 12, 31)),
            Arguments.of(PaxDate.of(7, 1, 2), LocalDate.of(7, 1, 1)),

            // Around the 400-year cycle edge (year 400 is not a leap year, 399 is)
            Arguments.of(PaxDate.of(399, 13, 6), LocalDate.of(399, 12, 3)),
            Arguments.of(PaxDate.of(399, 13, 7), LocalDate.of(399, 12, 4)),
            Arguments.of(PaxDate.of(399, 14, 1), LocalDate.of(399, 12, 5)),
            Arguments.of(PaxDate.of(399, 14, 2), LocalDate.of(399, 12, 6)),
            Arguments.of(PaxDate.of(399, 14, 3), LocalDate.of(399, 12, 7)),
            Arguments.of(PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29)),
            Arguments.of(PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30)),
            Arguments.of(PaxDate.of(401, 1, 1), LocalDate.of(400, 12, 31)),
            Arguments.of(PaxDate.of(401, 1, 2), LocalDate.of(401, 1, 1)),
            Arguments.of(PaxDate.of(401, 1, 3), LocalDate.of(401, 1, 2)),

            // Year 0 (leap year)
            Arguments.of(PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30)),
            Arguments.of(PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29)),

            // Assorted historical dates
            Arguments.of(PaxDate.of(1582, 10, 5), LocalDate.of(1582, 9, 9)),
            Arguments.of(PaxDate.of(1582, 10, 6), LocalDate.of(1582, 9, 10)),
            Arguments.of(PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10, 6)),
            Arguments.of(PaxDate.of(2012, 6, 23), LocalDate.of(2012, 6, 4)),
            Arguments.of(PaxDate.of(2012, 6, 24), LocalDate.of(2012, 6, 5)),

            // BCE years (proleptic years <= 0)
            Arguments.of(PaxDate.of(-6, 1, 1), LocalDate.of(-6, 1, 2)),
            Arguments.of(PaxDate.of(-6, 13, 6), LocalDate.of(-6, 12, 9)),
            Arguments.of(PaxDate.of(-6, 13, 7), LocalDate.of(-6, 12, 10)),
            Arguments.of(PaxDate.of(-6, 14, 1), LocalDate.of(-6, 12, 11)),
            Arguments.of(PaxDate.of(-6, 14, 2), LocalDate.of(-6, 12, 12)),
            Arguments.of(PaxDate.of(-6, 14, 27), LocalDate.of(-5, 1, 6)),
            Arguments.of(PaxDate.of(-6, 14, 28), LocalDate.of(-5, 1, 7)),
            Arguments.of(PaxDate.of(-5, 1, 1), LocalDate.of(-5, 1, 8)),
            Arguments.of(PaxDate.of(-5, 1, 2), LocalDate.of(-5, 1, 9)),
            Arguments.of(PaxDate.of(-99, 1, 1), LocalDate.of(-99, 1, 6)),
            Arguments.of(PaxDate.of(-99, 13, 6), LocalDate.of(-99, 12, 13)),
            Arguments.of(PaxDate.of(-99, 13, 7), LocalDate.of(-99, 12, 14)),
            Arguments.of(PaxDate.of(-99, 14, 1), LocalDate.of(-99, 12, 15)),
            Arguments.of(PaxDate.of(-99, 14, 2), LocalDate.of(-99, 12, 16)),
            Arguments.of(PaxDate.of(-100, 1, 1), LocalDate.of(-101, 12, 31)),
            Arguments.of(PaxDate.of(-100, 13, 6), LocalDate.of(-100, 12, 7)),
            Arguments.of(PaxDate.of(-100, 13, 7), LocalDate.of(-100, 12, 8)),
            Arguments.of(PaxDate.of(-100, 14, 1), LocalDate.of(-100, 12, 9)),
            Arguments.of(PaxDate.of(-100, 14, 2), LocalDate.of(-100, 12, 10)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_PaxDate_from_LocalDate(PaxDate expectedPaxDate, LocalDate isoDate) {
        assertEquals(expectedPaxDate, PaxDate.from(isoDate));
    }
}
