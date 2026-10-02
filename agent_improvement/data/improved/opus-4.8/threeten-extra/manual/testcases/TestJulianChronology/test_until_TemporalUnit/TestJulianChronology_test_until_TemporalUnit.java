package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JulianDate#until(java.time.temporal.Temporal, TemporalUnit)}.
 * <p>
 * Each case measures the amount of time between a {@code start} and an {@code end}
 * Julian date, expressed in a given {@link TemporalUnit}. The amount is truncated
 * towards zero (a partially elapsed unit counts as 0), so the cases come in pairs:
 * one just short of a whole unit and one that completes it.
 */
public class TestJulianChronology_test_until_TemporalUnit {

    /**
     * Cases of: startDate, endDate, unit, expectedAmount.
     * Grouped by unit; within each unit the dates are given as (year, month, day).
     */
    public static Stream<Arguments> data_until() {
        return Stream.of(
            // DAYS: counted exactly, may be negative when the end is before the start.
            untilCase(2014, 5, 26, 2014, 5, 26, DAYS, 0),
            untilCase(2014, 5, 26, 2014, 6, 1, DAYS, 6),
            untilCase(2014, 5, 26, 2014, 5, 20, DAYS, -6),

            // WEEKS: one whole week is reached only at start + 7 days.
            untilCase(2014, 5, 26, 2014, 5, 26, WEEKS, 0),
            untilCase(2014, 5, 26, 2014, 6, 1, WEEKS, 0),
            untilCase(2014, 5, 26, 2014, 6, 2, WEEKS, 1),

            // MONTHS: one whole month is reached only when the day-of-month is met again.
            untilCase(2014, 5, 26, 2014, 5, 26, MONTHS, 0),
            untilCase(2014, 5, 26, 2014, 6, 25, MONTHS, 0),
            untilCase(2014, 5, 26, 2014, 6, 26, MONTHS, 1),

            // YEARS: one whole year is reached only when the month/day is met again.
            untilCase(2014, 5, 26, 2014, 5, 26, YEARS, 0),
            untilCase(2014, 5, 26, 2015, 5, 25, YEARS, 0),
            untilCase(2014, 5, 26, 2015, 5, 26, YEARS, 1),

            // DECADES: one whole decade is 10 years.
            untilCase(2014, 5, 26, 2014, 5, 26, DECADES, 0),
            untilCase(2014, 5, 26, 2024, 5, 25, DECADES, 0),
            untilCase(2014, 5, 26, 2024, 5, 26, DECADES, 1),

            // CENTURIES: one whole century is 100 years.
            untilCase(2014, 5, 26, 2014, 5, 26, CENTURIES, 0),
            untilCase(2014, 5, 26, 2114, 5, 25, CENTURIES, 0),
            untilCase(2014, 5, 26, 2114, 5, 26, CENTURIES, 1),

            // MILLENNIA: one whole millennium is 1000 years.
            untilCase(2014, 5, 26, 2014, 5, 26, MILLENNIA, 0),
            untilCase(2014, 5, 26, 3014, 5, 25, MILLENNIA, 0),
            untilCase(2014, 5, 26, 3014, 5, 26, MILLENNIA, 1),

            // ERAS: crossing from the BC era (proleptic year <= 0) to the AD era is one era.
            untilCase(-2013, 5, 26, 0, 5, 26, ERAS, 0),
            untilCase(-2013, 5, 26, 2014, 5, 26, ERAS, 1)
        );
    }

    private static Arguments untilCase(int startYear, int startMonth, int startDay,
                                       int endYear, int endMonth, int endDay,
                                       TemporalUnit unit, long expectedAmount) {
        return Arguments.of(startYear, startMonth, startDay, endYear, endMonth, endDay, unit, expectedAmount);
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void until_returnsAmountOfTimeBetweenDatesInGivenUnit(
            int startYear, int startMonth, int startDay,
            int endYear, int endMonth, int endDay,
            TemporalUnit unit, long expectedAmount) {
        JulianDate start = JulianDate.of(startYear, startMonth, startDay);
        JulianDate end = JulianDate.of(endYear, endMonth, endDay);

        assertEquals(expectedAmount, start.until(end, unit));
    }
}
