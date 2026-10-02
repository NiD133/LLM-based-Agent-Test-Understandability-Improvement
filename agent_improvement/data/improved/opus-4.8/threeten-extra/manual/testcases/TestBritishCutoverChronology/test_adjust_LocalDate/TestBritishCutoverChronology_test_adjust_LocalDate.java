package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests that {@link BritishCutoverDate#with(java.time.temporal.TemporalAdjuster)} correctly
 * adjusts a {@code BritishCutoverDate} to the {@code BritishCutoverDate} that represents the
 * same calendar day as a given ISO {@link LocalDate}.
 *
 * <p>The interesting cases concern the British Julian-to-Gregorian cutover in September 1752,
 * where 1752-09-03 through 1752-09-13 were skipped. Adjusting to an ISO date inside that gap
 * resolves to the equivalent cutover date.
 */
public class TestBritishCutoverChronology_test_adjust_LocalDate {

    /**
     * Each case: the starting cutover date, the ISO {@link LocalDate} to adjust to,
     * and the expected resulting cutover date.
     */
    public static Stream<Arguments> data_withLocalDate() {
        return Stream.of(
            // ISO 1752-09-12 corresponds to cutover 1752-09-01 (just before the gap).
            Arguments.of(BritishCutoverDate.of(1752, 9, 2),  LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9, 1)),
            Arguments.of(BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9, 1)),
            // ISO 1752-09-14 corresponds to cutover 1752-09-14 (first day after the gap).
            Arguments.of(BritishCutoverDate.of(1752, 9, 2),  LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14)),
            Arguments.of(BritishCutoverDate.of(1752, 9, 15), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14)),
            // Well outside the cutover, ISO and cutover dates coincide.
            Arguments.of(BritishCutoverDate.of(2012, 2, 23), LocalDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 23))
        );
    }

    @ParameterizedTest
    @MethodSource("data_withLocalDate")
    public void test_adjust_LocalDate(BritishCutoverDate input, LocalDate local, BritishCutoverDate expected) {
        BritishCutoverDate adjusted = input.with(local);
        assertEquals(expected, adjusted);
    }
}
