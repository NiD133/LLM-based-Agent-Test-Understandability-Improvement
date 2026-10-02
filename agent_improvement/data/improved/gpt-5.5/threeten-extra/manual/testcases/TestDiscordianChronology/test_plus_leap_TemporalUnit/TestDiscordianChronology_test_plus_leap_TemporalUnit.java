package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_plus_leap_TemporalUnit {

    private static final int LEAP_YEAR = 2014;
    private static final int ST_TIBS_MONTH = 0;
    private static final int ST_TIBS_DAY = 0;

    public static Object[][] data_plus_leap() {
        return new Object[][] {
                leapDayPlus(0, DAYS, 2014, 0, 0),
                leapDayPlus(8, DAYS, 2014, 1, 67),
                leapDayPlus(-3, DAYS, 2014, 1, 57),

                leapDayPlus(0, WEEKS, 2014, 0, 0),
                leapDayPlus(3, WEEKS, 2014, 2, 2),
                leapDayPlus(-5, WEEKS, 2014, 1, 35),
                leapDayPlus(73 * 4, WEEKS, 2018, 0, 0),

                leapDayPlus(0, MONTHS, 2014, 0, 0),
                leapDayPlus(3, MONTHS, 2014, 4, 60),
                leapDayPlus(-5, MONTHS, 2013, 1, 60),
                leapDayPlus(20, MONTHS, 2018, 0, 0),

                leapDayPlus(0, YEARS, 2014, 0, 0),
                leapDayPlus(3, YEARS, 2017, 1, 60),
                leapDayPlus(-5, YEARS, 2009, 1, 60),
                leapDayPlus(4, YEARS, 2018, 0, 0),
        };
    }

    private static Object[] leapDayPlus(long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDayOfMonth) {
        return new Object[] {
                LEAP_YEAR,
                ST_TIBS_MONTH,
                ST_TIBS_DAY,
                amount,
                unit,
                expectedYear,
                expectedMonth,
                expectedDayOfMonth,
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(int year, int month, int dom, long amount,
            TemporalUnit unit, int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom),
                DiscordianDate.of(year, month, dom).plus(amount, unit));
    }
}
