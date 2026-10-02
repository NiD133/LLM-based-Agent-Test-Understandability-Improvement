package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Verifies that {@link LocalDate#until} returns a zero period when measured against the
 * {@link InternationalFixedDate} that represents the very same calendar day.
 *
 * <p>Each sample pairs an International Fixed date with its equivalent ISO {@link LocalDate}.
 * Because both refer to the same point on the timeline, the period from the ISO date up to the
 * fixed date must always be {@link Period#ZERO}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_until_InternationalFixedDate {

    /**
     * Pairs of (International Fixed date, equivalent ISO date) covering ordinary days,
     * leap years, the special "Leap Day"/"Year Day" boundaries, and a range of years.
     */
    public static Stream<Arguments> equivalentDatePairs() {
        return Stream.of(
            sample(1, 1, 1, 1, 1, 1),
            sample(1, 1, 2, 1, 1, 2),
            sample(1, 6, 27, 1, 6, 16),
            sample(1, 6, 28, 1, 6, 17),
            sample(1, 7, 1, 1, 6, 18),
            sample(1, 7, 2, 1, 6, 19),
            sample(1, 13, 28, 1, 12, 30),
            sample(1, 13, 27, 1, 12, 29),
            sample(1, 13, 29, 1, 12, 31),
            sample(2, 1, 1, 2, 1, 1),
            sample(4, 6, 27, 4, 6, 15),
            sample(4, 6, 28, 4, 6, 16),
            sample(4, 6, 29, 4, 6, 17),
            sample(4, 7, 1, 4, 6, 18),
            sample(4, 7, 2, 4, 6, 19),
            sample(4, 13, 28, 4, 12, 30),
            sample(4, 13, 27, 4, 12, 29),
            sample(4, 13, 29, 4, 12, 31),
            sample(5, 1, 1, 5, 1, 1),
            sample(100, 6, 27, 100, 6, 16),
            sample(100, 6, 28, 100, 6, 17),
            sample(100, 7, 1, 100, 6, 18),
            sample(100, 7, 2, 100, 6, 19),
            sample(400, 6, 27, 400, 6, 15),
            sample(400, 6, 28, 400, 6, 16),
            sample(400, 6, 29, 400, 6, 17),
            sample(400, 7, 1, 400, 6, 18),
            sample(400, 7, 2, 400, 6, 19),
            sample(1582, 9, 28, 1582, 9, 9),
            sample(1582, 10, 1, 1582, 9, 10),
            sample(1945, 10, 27, 1945, 10, 6),
            sample(2012, 6, 15, 2012, 6, 3),
            sample(2012, 6, 16, 2012, 6, 4));
    }

    private static Arguments sample(
            int fixedYear, int fixedMonth, int fixedDay,
            int isoYear, int isoMonth, int isoDay) {
        return Arguments.of(
            InternationalFixedDate.of(fixedYear, fixedMonth, fixedDay),
            LocalDate.of(isoYear, isoMonth, isoDay));
    }

    @ParameterizedTest
    @MethodSource("equivalentDatePairs")
    public void localDateUntilEquivalentFixedDateIsZero(InternationalFixedDate fixed, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(fixed));
    }
}
