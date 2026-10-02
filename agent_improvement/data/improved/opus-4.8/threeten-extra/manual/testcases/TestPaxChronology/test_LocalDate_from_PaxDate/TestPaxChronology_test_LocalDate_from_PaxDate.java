package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests that a {@link PaxDate} can be converted to the equivalent ISO
 * {@link LocalDate} via {@link LocalDate#from(java.time.temporal.TemporalAccessor)}.
 * <p>
 * Each test case pairs a Pax date with the ISO date it should map to. The Pax
 * calendar is anchored such that {@code 0001-01-01 (Pax)} equals
 * {@code 0000-12-31 (ISO)}, so the two representations rarely share the same
 * day, month or year numbers.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_LocalDate_from_PaxDate {

    /**
     * Supplies {@code (paxDate, expectedIsoDate)} pairs covering a range of
     * calendar features: the epoch boundary, leap years (with the inserted
     * one-week 'Pax' month), century/quad-century rules, and dates in the
     * 'Before Current Era' (negative proleptic years).
     */
    static Stream<Arguments> paxDateToIsoDateSamples() {
        return Stream.of(
                // Start of the Pax epoch, mapping back one day into ISO year 0
                arguments(PaxDate.of(1, 1, 1), LocalDate.of(0, 12, 31)),
                arguments(PaxDate.of(1, 1, 2), LocalDate.of(1, 1, 1)),
                arguments(PaxDate.of(1, 1, 3), LocalDate.of(1, 1, 2)),
                arguments(PaxDate.of(1, 1, 28), LocalDate.of(1, 1, 27)),
                arguments(PaxDate.of(1, 2, 1), LocalDate.of(1, 1, 28)),
                arguments(PaxDate.of(1, 2, 2), LocalDate.of(1, 1, 29)),
                arguments(PaxDate.of(1, 2, 3), LocalDate.of(1, 1, 30)),

                // Year 6 is a leap year: month 13 is the short 'Pax' month, month 14 follows
                arguments(PaxDate.of(6, 13, 6), LocalDate.of(6, 12, 1)),
                arguments(PaxDate.of(6, 13, 7), LocalDate.of(6, 12, 2)),
                arguments(PaxDate.of(6, 14, 1), LocalDate.of(6, 12, 3)),
                arguments(PaxDate.of(6, 14, 2), LocalDate.of(6, 12, 4)),
                arguments(PaxDate.of(6, 14, 3), LocalDate.of(6, 12, 5)),
                arguments(PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29)),
                arguments(PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30)),
                arguments(PaxDate.of(7, 1, 1), LocalDate.of(6, 12, 31)),
                arguments(PaxDate.of(7, 1, 2), LocalDate.of(7, 1, 1)),

                // Year 399 is a leap year; year 400 is not (divisible by 400)
                arguments(PaxDate.of(399, 13, 6), LocalDate.of(399, 12, 3)),
                arguments(PaxDate.of(399, 13, 7), LocalDate.of(399, 12, 4)),
                arguments(PaxDate.of(399, 14, 1), LocalDate.of(399, 12, 5)),
                arguments(PaxDate.of(399, 14, 2), LocalDate.of(399, 12, 6)),
                arguments(PaxDate.of(399, 14, 3), LocalDate.of(399, 12, 7)),
                arguments(PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29)),
                arguments(PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30)),
                arguments(PaxDate.of(401, 1, 1), LocalDate.of(400, 12, 31)),
                arguments(PaxDate.of(401, 1, 2), LocalDate.of(401, 1, 1)),
                arguments(PaxDate.of(401, 1, 3), LocalDate.of(401, 1, 2)),

                // Year 0 (a non-leap year at the era boundary)
                arguments(PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30)),
                arguments(PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29)),

                // Assorted historically notable dates
                arguments(PaxDate.of(1582, 10, 5), LocalDate.of(1582, 9, 9)),
                arguments(PaxDate.of(1582, 10, 6), LocalDate.of(1582, 9, 10)),
                arguments(PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10, 6)),
                arguments(PaxDate.of(2012, 6, 23), LocalDate.of(2012, 6, 4)),
                arguments(PaxDate.of(2012, 6, 24), LocalDate.of(2012, 6, 5)),

                // 'Before Current Era': negative proleptic years
                arguments(PaxDate.of(-6, 1, 1), LocalDate.of(-6, 1, 2)),
                arguments(PaxDate.of(-6, 13, 6), LocalDate.of(-6, 12, 9)),
                arguments(PaxDate.of(-6, 13, 7), LocalDate.of(-6, 12, 10)),
                arguments(PaxDate.of(-6, 14, 1), LocalDate.of(-6, 12, 11)),
                arguments(PaxDate.of(-6, 14, 2), LocalDate.of(-6, 12, 12)),
                arguments(PaxDate.of(-6, 14, 27), LocalDate.of(-5, 1, 6)),
                arguments(PaxDate.of(-6, 14, 28), LocalDate.of(-5, 1, 7)),
                arguments(PaxDate.of(-5, 1, 1), LocalDate.of(-5, 1, 8)),
                arguments(PaxDate.of(-5, 1, 2), LocalDate.of(-5, 1, 9)),
                arguments(PaxDate.of(-99, 1, 1), LocalDate.of(-99, 1, 6)),
                arguments(PaxDate.of(-99, 13, 6), LocalDate.of(-99, 12, 13)),
                arguments(PaxDate.of(-99, 13, 7), LocalDate.of(-99, 12, 14)),
                arguments(PaxDate.of(-99, 14, 1), LocalDate.of(-99, 12, 15)),
                arguments(PaxDate.of(-99, 14, 2), LocalDate.of(-99, 12, 16)),
                arguments(PaxDate.of(-100, 1, 1), LocalDate.of(-101, 12, 31)),
                arguments(PaxDate.of(-100, 13, 6), LocalDate.of(-100, 12, 7)),
                arguments(PaxDate.of(-100, 13, 7), LocalDate.of(-100, 12, 8)),
                arguments(PaxDate.of(-100, 14, 1), LocalDate.of(-100, 12, 9)),
                arguments(PaxDate.of(-100, 14, 2), LocalDate.of(-100, 12, 10)));
    }

    private static Arguments arguments(PaxDate pax, LocalDate iso) {
        return Arguments.of(pax, iso);
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("paxDateToIsoDateSamples")
    public void convertsPaxDateToIsoLocalDate(PaxDate pax, LocalDate expectedIso) {
        assertEquals(expectedIso, LocalDate.from(pax));
    }
}
