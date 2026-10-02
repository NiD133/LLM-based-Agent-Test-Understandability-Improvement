/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link DiscordianChronology} and {@link DiscordianDate}.
 *
 * <p>The Discordian calendar has 5 months of 73 days each (365 days/year).
 * Years are offset from ISO by +1166: Discordian year 1167 = ISO year 1.
 * Leap years mirror ISO leap years (every 4 years, except century years not
 * divisible by 400). Leap years have an extra day called "St. Tib's Day"
 * (encoded as month=0, day=0), inserted between day 59 and day 60 of month 1.
 */
public class TestDiscordianChronology {

    //-----------------------------------------------------------------------
    // Chronology.of(String)
    //-----------------------------------------------------------------------
    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("Discordian");
        assertNotNull(chrono);
        assertEquals(DiscordianChronology.INSTANCE, chrono);
        assertEquals("Discordian", chrono.getId());
        assertEquals("discordian", chrono.getCalendarType());
    }

    @Test
    public void test_chronology_of_name_id() {
        Chronology chrono = Chronology.of("discordian");
        assertNotNull(chrono);
        assertEquals(DiscordianChronology.INSTANCE, chrono);
        assertEquals("Discordian", chrono.getId());
        assertEquals("discordian", chrono.getCalendarType());
    }

    //-----------------------------------------------------------------------
    // creation, toLocalDate()
    //-----------------------------------------------------------------------
    /**
     * Pairs of (DiscordianDate, equivalent ISO LocalDate) covering:
     * ancient dates, year-1 CE boundaries, leap-day insertion (St. Tib's Day),
     * century non-leap years, year-end dates, and modern dates.
     *
     * <p>Discordian year = ISO year + 1166. For example, ISO year 1 = Discordian 1167.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Ancient dates: ISO BCE years map to low Discordian years
            {DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1)},
            {DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1)},
            {DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1)},
            {DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1)},

            // ISO year 1 CE = Discordian year 1167: first few days of the calendar
            {DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1)},
            {DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2)},
            {DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3)},

            // End of Discordian month 1 in a non-leap year: day 59 is Feb 28,
            // day 60 jumps directly to March 1 (no St. Tib's Day in non-leap year)
            {DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26)},
            {DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27)},
            {DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28)},
            {DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1)},

            // ISO year 4 CE = Discordian year 1170: a leap year.
            // St. Tib's Day (month=0, day=0) falls on Feb 29; day 60 of month 1
            // then corresponds to March 1.
            {DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26)},
            {DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27)},
            {DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28)},
            {DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29)},   // St. Tib's Day = Feb 29
            {DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1)},

            // ISO year 100 CE = Discordian year 1266: a century non-leap year
            // (divisible by 100 but not 400), so no St. Tib's Day.
            {DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26)},
            {DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27)},
            {DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28)},
            {DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1)},
            {DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2)},

            // Year-end: last two days of Discordian year 1166 (ISO year 0)
            {DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31)},
            {DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30)},

            // Historical dates around the Gregorian calendar reform (1582)
            {DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14)},
            {DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15)},

            // A modern mid-20th century date
            {DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12)},

            // Modern dates in 2012
            {DiscordianDate.of(3178, 3, 40), LocalDate.of(2012, 7, 5)},
            {DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso, LocalDate.from(discordian));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_from_LocalDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(discordian, DiscordianDate.from(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_chronology_dateEpochDay(DiscordianDate discordian, LocalDate iso) {
        assertEquals(discordian, DiscordianChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_toEpochDay(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso.toEpochDay(), discordian.toEpochDay());
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_until_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordian.until(discordian));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_until_LocalDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordian.until(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(discordian));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(DiscordianDate discordian, LocalDate iso) {
        assertEquals(discordian, DiscordianChronology.INSTANCE.date(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso, LocalDate.from(discordian.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(discordian.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(discordian.plus(35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(discordian.plus(-1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(discordian.plus(-60, DAYS)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso, LocalDate.from(discordian.minus(0, DAYS)));
        assertEquals(iso.minusDays(1), LocalDate.from(discordian.minus(1, DAYS)));
        assertEquals(iso.minusDays(35), LocalDate.from(discordian.minus(35, DAYS)));
        assertEquals(iso.minusDays(-1), LocalDate.from(discordian.minus(-1, DAYS)));
        assertEquals(iso.minusDays(-60), LocalDate.from(discordian.minus(-60, DAYS)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(DiscordianDate discordian, LocalDate iso) {
        assertEquals(0, discordian.until(iso.plusDays(0), DAYS));
        assertEquals(1, discordian.until(iso.plusDays(1), DAYS));
        assertEquals(35, discordian.until(iso.plusDays(35), DAYS));
        assertEquals(-40, discordian.until(iso.minusDays(40), DAYS));
    }

    /**
     * Invalid (year, month, day) combinations that must throw DateTimeException.
     *
     * <p>Column layout: {year, month, dayOfMonth}
     * <ul>
     *   <li>St. Tib's Day (month=0, day=0) is only valid in a leap year</li>
     *   <li>Month must be 1–5 (0 is reserved for St. Tib's Day in leap years only)</li>
     *   <li>Day-of-month must be 1–73 for regular months</li>
     * </ul>
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // St. Tib's Day in a non-leap year (1900 is not a leap year)
            {1900, 0, 0},

            // Invalid month values
            {1900, -1, 1},
            {1900, 0, 1},   // month=0 with day != 0 is always invalid
            {1900, 6, 1},
            {1900, 7, 1},

            // Day out of range for month 1
            {1900, 1, -1},
            {1900, 1, 0},
            {1900, 1, 74},

            // St. Tib's Day again in a non-leap year (duplicate, confirms consistency)
            {1900, 0, 0},

            // Day out of range for month 5
            {1900, 5, -1},
            {1900, 5, 0},
            {1900, 5, 74},

            // Day 74 is out of range for months 2–4 as well
            {1900, 2, 74},
            {1900, 3, 74},
            {1900, 4, 74},
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dom));
    }

    @Test
    public void test_chronology_dateYearDay_badDate() {
        // Day 366 is only valid in a leap year; 2001 is not a leap year
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.dateYearDay(2001, 366));
    }

    //-----------------------------------------------------------------------
    // isLeapYear()
    //-----------------------------------------------------------------------
    @Test
    public void test_isLeapYear_loop() {
        // Discordian leap years mirror ISO leap years; the ISO year is (discordianYear - 1166).
        // A year is a leap year if (isoYear % 4 == 0) && (isoYear % 400 == 0 || isoYear % 100 != 0).
        IntPredicate isLeapYear = year -> {
            int offsetYear = year - 1166;
            return offsetYear % 4 == 0 && (offsetYear % 400 == 0 || offsetYear % 100 != 0);
        };
        for (int year = 1066; year < 1567; year++) {
            DiscordianDate base = DiscordianDate.of(year, 1, 1);
            assertEquals(isLeapYear.test(year), base.isLeapYear());
            assertEquals(isLeapYear.test(year), DiscordianChronology.INSTANCE.isLeapYear(year));
        }
    }

    @Test
    public void test_isLeapYear_specific() {
        // Discordian year 1174 = ISO year 8: leap year (8 % 4 == 0, not a century year)
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1174));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1173));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1172));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1171));
        // Discordian year 1170 = ISO year 4: first post-epoch leap year
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1170));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1169));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1168));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1167));
        // Discordian year 1166 = ISO year 0: leap year (0 % 400 == 0)
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1166));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1165));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1164));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1163));
        // Discordian year 1162 = ISO year -4: leap year (-4 % 4 == 0, not a century)
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1162));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1161));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1160));
    }

    /**
     * Each regular Discordian month always has exactly 73 days, regardless of whether
     * the year is a leap year (the leap day is in its own pseudo-month 0).
     *
     * <p>Column layout: {year, month, expectedLength}
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // All 5 months of a non-leap year each have 73 days
            {1900, 1, 73},
            {1900, 2, 73},
            {1900, 3, 73},
            {1900, 4, 73},
            {1900, 5, 73},

            // Month 1 of various non-leap and leap years: always 73
            {1901, 1, 73},
            {1902, 1, 73},
            {1903, 1, 73},
            {1904, 1, 73},
            {1966, 1, 73},
            {2066, 1, 73},
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, DiscordianDate.of(year, month, 1).lengthOfMonth());
    }

    @Test
    public void test_lengthOfMonth_specific() {
        // St. Tib's Day pseudo-month (month=0) always has exactly 1 day
        assertEquals(1, DiscordianDate.of(3178, 0, 0).lengthOfMonth());
        // Regular months always have 73 days (first and last day of the month)
        assertEquals(73, DiscordianDate.of(3178, 1, 1).lengthOfMonth());
        assertEquals(73, DiscordianDate.of(3178, 1, 73).lengthOfMonth());
    }

    //-----------------------------------------------------------------------
    // era, prolepticYear and dateYearDay
    //-----------------------------------------------------------------------
    @Test
    public void test_era_loop() {
        for (int year = 1; year < 200; year++) {
            DiscordianDate base = DiscordianChronology.INSTANCE.date(year, 1, 1);
            assertEquals(year, base.get(YEAR));
            assertEquals(DiscordianEra.YOLD, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));
            DiscordianDate eraBased = DiscordianChronology.INSTANCE.date(DiscordianEra.YOLD, year, 1, 1);
            assertEquals(base, eraBased);
        }
    }

    @Test
    public void test_era_yearDay_loop() {
        for (int year = 1; year < 200; year++) {
            DiscordianDate base = DiscordianChronology.INSTANCE.dateYearDay(year, 1);
            assertEquals(year, base.get(YEAR));
            assertEquals(DiscordianEra.YOLD, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));
            DiscordianDate eraBased = DiscordianChronology.INSTANCE.dateYearDay(DiscordianEra.YOLD, year, 1);
            assertEquals(base, eraBased);
        }
    }

    @Test
    public void test_prolepticYear_specific() {
        // In the Discordian calendar there is only one era (YOLD), so the proleptic year
        // equals the year-of-era directly.
        assertEquals(4, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 4));
        assertEquals(3, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 3));
        assertEquals(2, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 2));
        assertEquals(1, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 1));
    }

    @Test
    public void test_prolepticYear_badEra() {
        // Passing an ISO era instead of a DiscordianEra must throw ClassCastException
        assertThrows(ClassCastException.class, () -> DiscordianChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }

    @Test
    public void test_Chronology_eraOf() {
        // The only valid era ordinal is 1 (YOLD)
        assertEquals(DiscordianEra.YOLD, DiscordianChronology.INSTANCE.eraOf(1));
    }

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Era ordinals other than 1 are not defined in the Discordian calendar
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.eraOf(2));
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.eraOf(0));
    }

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = DiscordianChronology.INSTANCE.eras();
        assertEquals(1, eras.size());
        assertTrue(eras.contains(DiscordianEra.YOLD));
    }

    //-----------------------------------------------------------------------
    // Chronology.range
    //-----------------------------------------------------------------------
    @Test
    public void test_Chronology_range() {
        assertEquals(ValueRange.of(0, 1, 0, 5), DiscordianChronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(0, 1, 5, 5), DiscordianChronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 15), DiscordianChronology.INSTANCE.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(0, 1, 73, 73), DiscordianChronology.INSTANCE.range(ALIGNED_WEEK_OF_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 5), DiscordianChronology.INSTANCE.range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(0, 1, 0, 73), DiscordianChronology.INSTANCE.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 365, 366), DiscordianChronology.INSTANCE.range(DAY_OF_YEAR));
        assertEquals(ValueRange.of(-1_145_400, 999_999 * 365L + 242_499), DiscordianChronology.INSTANCE.range(EPOCH_DAY));
        assertEquals(ValueRange.of(1, 1), DiscordianChronology.INSTANCE.range(ERA));
        assertEquals(ValueRange.of(0, 1, 5, 5), DiscordianChronology.INSTANCE.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(0, 999_999 * 5L + 5 - 1), DiscordianChronology.INSTANCE.range(PROLEPTIC_MONTH));
        assertEquals(ValueRange.of(1, 999_999), DiscordianChronology.INSTANCE.range(YEAR));
        assertEquals(ValueRange.of(1, 999_999), DiscordianChronology.INSTANCE.range(YEAR_OF_ERA));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.range
    //-----------------------------------------------------------------------
    /**
     * Per-date field ranges. The Discordian calendar has special behaviour for St. Tib's Day
     * (month=0, day=0) and for leap years vs non-leap years. The minimum/maximum values for
     * several fields shift depending on whether a leap day is present in the year.
     *
     * <p>Column layout: {year, month, dom, field, expectedMin, expectedMax}
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: St Tib's Day is its own pseudo-month, so range is (0 to 0);
            // regular months always have days 1 to 73.
            {2010, 0, 0, DAY_OF_MONTH, 0, 0},
            {2010, 1, 23, DAY_OF_MONTH, 1, 73},
            {2010, 2, 23, DAY_OF_MONTH, 1, 73},
            {2010, 3, 23, DAY_OF_MONTH, 1, 73},
            {2010, 4, 23, DAY_OF_MONTH, 1, 73},
            {2010, 5, 23, DAY_OF_MONTH, 1, 73},

            // DAY_OF_YEAR: ordinal count includes St. Tib's Day; leap year has 366 days.
            {2010, 0, 0, DAY_OF_YEAR, 1, 366},
            {2010, 1, 23, DAY_OF_YEAR, 1, 366},
            {2011, 2, 23, DAY_OF_YEAR, 1, 365},

            // MONTH_OF_YEAR: St Tib's Day still belongs to the leap year, so month range
            // in leap years is (0 to 5) to accommodate pseudo-month 0.
            {2010, 0, 0, MONTH_OF_YEAR, 0, 5},
            {2010, 1, 1, MONTH_OF_YEAR, 0, 5},
            {2011, 1, 23, MONTH_OF_YEAR, 1, 5},

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: St Tib's Day is in its own pseudo-month,
            // so range is (0 to 0); regular months have days-of-week 1 to 5.
            {2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 0},
            {2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5},
            {2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5},
            {2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5},

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: in a leap year, St Tib's Day is in the year
            // so the range starts at 0; non-leap years start at 1.
            {2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2011, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 5},

            // ALIGNED_WEEK_OF_MONTH: St Tib's Day is in its own pseudo-month (0 to 0);
            // regular months have weeks 1 to 15 (73 days / 5 days per week = 14.6, so 15).
            {2010, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 0},
            {2010, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 15},

            // ALIGNED_WEEK_OF_YEAR: leap year includes St Tib's Day pseudo-week (0 to 73);
            // non-leap years have weeks 1 to 73.
            {2010, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 73},
            {2010, 1, 23, ALIGNED_WEEK_OF_YEAR, 0, 73},
            {2011, 1, 23, ALIGNED_WEEK_OF_YEAR, 1, 73},

            // DAY_OF_WEEK: St Tib's Day is in its own pseudo-week (0 to 0);
            // regular days have day-of-week 1 to 5.
            {2010, 0, 0, DAY_OF_WEEK, 0, 0},
            {2010, 1, 1, DAY_OF_WEEK, 1, 5},
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), DiscordianDate.of(year, month, dom).range(field));
    }

    @Test
    public void test_range_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> DiscordianDate.of(2012, 5, 30).range(MINUTE_OF_DAY));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.getLong
    //-----------------------------------------------------------------------
    /**
     * Expected long values for temporal fields on specific Discordian dates.
     *
     * <p>Column layout: {year, month, dom, field, expectedValue}
     *
     * <p>The PROLEPTIC_MONTH formula is: year * 5 + month - 1.
     * St. Tib's Day (month=0, day=0) uses pseudo-month 0 for positional fields.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // Date in month 1 (Chaos), day 26 — early in the first month of the year
            {2014, 1, 26, DAY_OF_WEEK, 1},
            {2014, 1, 26, DAY_OF_MONTH, 26},
            {2014, 1, 26, DAY_OF_YEAR, 26},
            {2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1},
            {2014, 1, 26, ALIGNED_WEEK_OF_MONTH, 6},
            {2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1},
            {2014, 1, 26, ALIGNED_WEEK_OF_YEAR, 6},
            {2014, 1, 26, MONTH_OF_YEAR, 1},
            {2014, 1, 26, PROLEPTIC_MONTH, 2014 * 5 + 1 - 1},  // year * 5 + month - 1
            {2014, 1, 26, YEAR, 2014},
            {2014, 1, 26, ERA, 1},

            // Date in month 5 (Aftermath), day 26 — same day-of-month, later in year
            {2014, 5, 26, DAY_OF_WEEK, 3},
            {2014, 5, 26, DAY_OF_MONTH, 26},
            {2014, 5, 26, DAY_OF_YEAR, 1 + 73 + 73 + 73 + 73 + 26},  // St.Tib + 4*73 + day 26
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 6},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 64},
            {2014, 5, 26, MONTH_OF_YEAR, 5},
            {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1},  // year * 5 + month - 1
            {2014, 5, 26, YEAR, 2014},
            {2014, 5, 26, ERA, 1},
            {1, 5, 8, ERA, 1},

            // St. Tib's Day (month=0, day=0) in a leap year: all positional fields are 0
            // except DAY_OF_YEAR (which is ordinal 60) and PROLEPTIC_MONTH (uses month 1 slot)
            {2014, 0, 0, DAY_OF_WEEK, 0},
            {2014, 0, 0, DAY_OF_MONTH, 0},
            {2014, 0, 0, DAY_OF_YEAR, 60},   // ordinal position between day 59 and day 60 of month 1
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
            {2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 0},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
            {2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 0},
            {2014, 0, 0, MONTH_OF_YEAR, 0},
            {2014, 0, 0, PROLEPTIC_MONTH, 2014 * 5 + 1 - 1},  // St. Tib's Day belongs to the first month slot
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, DiscordianDate.of(year, month, dom).getLong(field));
    }

    @Test
    public void test_getLong_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> DiscordianDate.of(2012, 1, 30).getLong(MINUTE_OF_DAY));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.with
    //-----------------------------------------------------------------------
    /**
     * Inputs and expected results for {@code DiscordianDate.with(TemporalField, long)}.
     *
     * <p>Column layout: {year, month, dom, field, value, expectedYear, expectedMonth, expectedDom}
     *
     * <p>Notable behaviours:
     * <ul>
     *   <li>Setting a field to its current value must be a no-op (the date is unchanged).</li>
     *   <li>St. Tib's Day (0,0) has special semantics: setting positional fields to 0
     *       keeps the date on St. Tib's Day; any positive value moves to a regular day.</li>
     *   <li>Setting DAY_OF_MONTH=0 from a regular date moves to St. Tib's Day (leap years only).</li>
     *   <li>Setting YEAR to a non-leap year while on St. Tib's Day moves to day 60 of month 1.</li>
     * </ul>
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // --- Operations on a regular date (year=2014, month=5, day=26) ---
            {2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 24},
            {2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 26},          // no-op: already day-of-week 3
            {2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31},
            {2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26},         // no-op
            {2014, 5, 26, DAY_OF_YEAR, 365, 2014, 5, 72},
            {2014, 5, 26, DAY_OF_YEAR, 319, 2014, 5, 26},         // no-op
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 28},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 5, 26},  // no-op
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 1},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 6, 2014, 5, 26},  // no-op
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 25},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 26},   // no-op
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 2, 40},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 64, 2014, 5, 26},  // no-op
            {2014, 5, 26, MONTH_OF_YEAR, 4, 2014, 4, 26},
            {2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26},          // no-op
            {2014, 5, 26, PROLEPTIC_MONTH, 2013 * 5 + 3 - 1, 2013, 3, 26},
            {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1, 2014, 5, 26},  // no-op
            {2014, 5, 26, YEAR, 2012, 2012, 5, 26},
            {2014, 5, 26, YEAR, 2014, 2014, 5, 26},                // no-op
            {2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26},
            {2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26},         // no-op
            {2014, 5, 26, ERA, 1, 2014, 5, 26},                    // no-op (only one era)

            // --- Operations starting from St. Tib's Day (year=2014, month=0, day=0) ---
            // Setting a positional field to 0 keeps the date on St. Tib's Day
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 2014, 0, 0},   // no-op
            // Setting to a positive value moves out of St. Tib's Day pseudo-month
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 1, 56},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2014, 1, 57},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 1, 58},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2014, 1, 59},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 1, 60},

            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 2014, 0, 0},    // no-op
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2014, 1, 56},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 1, 57},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 1, 58},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2014, 1, 59},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 1, 60},

            {2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 2014, 0, 0},    // no-op
            {2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 3, 2014, 1, 15},

            {2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 2014, 0, 0},     // no-op
            {2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 3, 2014, 1, 15},

            {2014, 0, 0, DAY_OF_WEEK, 0, 2014, 0, 0},              // no-op
            {2014, 0, 0, DAY_OF_WEEK, 1, 2014, 1, 56},
            {2014, 0, 0, DAY_OF_WEEK, 2, 2014, 1, 57},
            {2014, 0, 0, DAY_OF_WEEK, 3, 2014, 1, 58},
            {2014, 0, 0, DAY_OF_WEEK, 4, 2014, 1, 59},
            {2014, 0, 0, DAY_OF_WEEK, 5, 2014, 1, 60},

            {2014, 0, 0, DAY_OF_MONTH, 0, 2014, 0, 0},             // no-op
            {2014, 0, 0, DAY_OF_MONTH, 3, 2014, 1, 3},

            {2014, 0, 0, MONTH_OF_YEAR, 0, 2014, 0, 0},            // no-op
            {2014, 0, 0, MONTH_OF_YEAR, 1, 2014, 1, 60},           // moves to day 60 (after St. Tib's)
            {2014, 0, 0, MONTH_OF_YEAR, 2, 2014, 2, 60},

            // Setting YEAR to a leap year preserves St. Tib's Day; non-leap year moves to day 60
            {2014, 0, 0, YEAR, 2014, 2014, 0, 0},                  // 2014 is a leap year — no-op
            {2014, 0, 0, YEAR, 2013, 2013, 1, 60},                 // 2013 is not a leap year
            {2014, 0, 0, YEAR, 2015, 2015, 1, 60},                 // 2015 is not a leap year
            {2014, 0, 0, YEAR, 2018, 2018, 0, 0},                  // 2018 is a leap year

            // --- Setting DAY_OF_MONTH=0 on a regular date moves to St. Tib's Day (only valid in leap year) ---
            {2014, 3, 31, DAY_OF_MONTH, 0, 2014, 0, 0},
            {2014, 1, 31, DAY_OF_MONTH, 0, 2014, 0, 0},

            // Setting MONTH_OF_YEAR=0 on a regular date moves to St. Tib's Day (only valid in leap year)
            {2014, 3, 31, MONTH_OF_YEAR, 0, 2014, 0, 0},

            // Setting DAY_OF_YEAR=60 in a leap year lands on St. Tib's Day
            {2014, 3, 31, DAY_OF_YEAR, 60, 2014, 0, 0},
            {2013, 3, 31, DAY_OF_YEAR, 60, 2013, 1, 60},           // non-leap: day 60 is a regular day

            // Moving from a date near St. Tib's Day across years
            {2013, 1, 60, YEAR, 2014, 2014, 1, 60},
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom), DiscordianDate.of(year, month, dom).with(field, value));
    }

    /**
     * Invalid values for {@code with(TemporalField, long)} that must throw DateTimeException.
     *
     * <p>Column layout: {year, month, dom, field, invalidValue}
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // DAY_OF_WEEK: valid range is 1–5 for regular dates; 0 and 6 are out of range
            {2013, 1, 1, DAY_OF_WEEK, 0},
            {2013, 1, 1, DAY_OF_WEEK, 6},
            {2014, 1, 1, DAY_OF_WEEK, -1},
            {2014, 1, 1, DAY_OF_WEEK, 6},

            // DAY_OF_MONTH: valid range is 1–73 for regular months
            {2013, 1, 1, DAY_OF_MONTH, 0},
            {2013, 1, 1, DAY_OF_MONTH, 74},
            {2014, 1, 1, DAY_OF_MONTH, -1},
            {2014, 1, 1, DAY_OF_MONTH, 74},

            // DAY_OF_YEAR: valid range is 1–365 (non-leap) or 1–366 (leap); 0 and 367 are invalid
            {2013, 1, 1, DAY_OF_YEAR, 0},
            {2014, 1, 1, DAY_OF_YEAR, 0},
            {2013, 1, 1, DAY_OF_YEAR, 367},
            {2014, 1, 1, DAY_OF_YEAR, 367},

            // MONTH_OF_YEAR: valid range is 1–5 for non-leap, 0–5 for leap; negatives always invalid
            {2013, 1, 1, MONTH_OF_YEAR, 0},    // non-leap year: month 0 (St. Tib's Day) is invalid
            {2013, 1, 1, MONTH_OF_YEAR, 6},
            {2014, 1, 1, MONTH_OF_YEAR, -1},
            {2014, 1, 1, MONTH_OF_YEAR, 6},
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dom).with(field, value));
    }

    @Test
    public void test_with_TemporalField_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> DiscordianDate.of(2012, 5, 30).with(MINUTE_OF_DAY, 0));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.with(TemporalAdjuster)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjust1() {
        // St. Tib's Day is its own pseudo-month of length 1, so lastDayOfMonth is a no-op
        DiscordianDate base = DiscordianDate.of(2014, 0, 0);
        DiscordianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(DiscordianDate.of(2014, 0, 0), test);
    }

    @Test
    public void test_adjust2() {
        // Regular month: lastDayOfMonth moves to day 73
        DiscordianDate base = DiscordianDate.of(2012, 2, 23);
        DiscordianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(DiscordianDate.of(2012, 2, 73), test);
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.with(Local*)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjust_toLocalDate() {
        DiscordianDate discordian = DiscordianDate.of(2000, 1, 4);
        DiscordianDate test = discordian.with(LocalDate.of(2012, 7, 6));
        assertEquals(DiscordianDate.of(3178, 3, 41), test);
    }

    @Test
    public void test_adjust_toMonth() {
        // java.time.Month is an ISO concept; cannot be used as a Discordian temporal adjuster
        DiscordianDate discordian = DiscordianDate.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> discordian.with(Month.APRIL));
    }

    //-----------------------------------------------------------------------
    // LocalDate.with(DiscordianDate)
    //-----------------------------------------------------------------------
    @Test
    public void test_LocalDate_adjustToDiscordianDate() {
        DiscordianDate discordian = DiscordianDate.of(3178, 3, 41);
        LocalDate test = LocalDate.MIN.with(discordian);
        assertEquals(LocalDate.of(2012, 7, 6), test);
    }

    @Test
    public void test_LocalDateTime_adjustToDiscordianDate() {
        DiscordianDate discordian = DiscordianDate.of(3178, 3, 41);
        LocalDateTime test = LocalDateTime.MIN.with(discordian);
        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), test);
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.plus
    //-----------------------------------------------------------------------
    /**
     * Inputs and expected results for {@code DiscordianDate.plus(long, TemporalUnit)}.
     *
     * <p>Column layout: {year, month, dom, amount, unit, expectedYear, expectedMonth, expectedDom}
     *
     * <p>Note: {@code test_minus_TemporalUnit} reuses this data source with the column order
     * reversed — the "expected" columns become the starting date and the "input" columns become
     * the destination, verifying that subtracting {@code amount} undoes the addition.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // Adding DAYS
            {2014, 5, 26, 0, DAYS, 2014, 5, 26},
            {2014, 5, 26, 8, DAYS, 2014, 5, 34},
            {2014, 5, 26, -3, DAYS, 2014, 5, 23},
            // Adding WEEKS (1 Discordian week = 5 days)
            {2014, 5, 26, 0, WEEKS, 2014, 5, 26},
            {2014, 5, 26, 3, WEEKS, 2014, 5, 41},
            {2014, 5, 26, -5, WEEKS, 2014, 5, 1},
            // Adding MONTHS (1 Discordian month = 73 days)
            {2014, 5, 26, 0, MONTHS, 2014, 5, 26},
            {2014, 5, 26, 3, MONTHS, 2015, 3, 26},
            {2014, 5, 26, -5, MONTHS, 2013, 5, 26},
            // Adding YEARS
            {2014, 5, 26, 0, YEARS, 2014, 5, 26},
            {2014, 5, 26, 3, YEARS, 2017, 5, 26},
            {2014, 5, 26, -5, YEARS, 2009, 5, 26},
            // Adding DECADES
            {2014, 5, 26, 0, DECADES, 2014, 5, 26},
            {2014, 5, 26, 3, DECADES, 2044, 5, 26},
            {2014, 5, 26, -5, DECADES, 1964, 5, 26},
            // Adding CENTURIES
            {2014, 5, 26, 0, CENTURIES, 2014, 5, 26},
            {2014, 5, 26, 3, CENTURIES, 2314, 5, 26},
            {2014, 5, 26, -5, CENTURIES, 1514, 5, 26},
            // Adding MILLENNIA
            {2014, 5, 26, 0, MILLENNIA, 2014, 5, 26},
            {2014, 5, 26, 3, MILLENNIA, 5014, 5, 26},
            {2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26},
        };
    }

    /**
     * Inputs and expected results for {@code plus()} when starting from St. Tib's Day.
     *
     * <p>Column layout: {year, month, dom, amount, unit, expectedYear, expectedMonth, expectedDom}
     *
     * <p>St. Tib's Day (month=0, day=0) has special arithmetic: adding an amount that
     * spans to the next leap year may land back on St. Tib's Day again.
     */
    public static Object[][] data_plus_leap() {
        return new Object[][] {
            // DAYS from St. Tib's Day
            {2014, 0, 0, 0, DAYS, 2014, 0, 0},
            {2014, 0, 0, 8, DAYS, 2014, 1, 67},
            {2014, 0, 0, -3, DAYS, 2014, 1, 57},
            // WEEKS from St. Tib's Day (1 week = 5 days)
            {2014, 0, 0, 0, WEEKS, 2014, 0, 0},
            {2014, 0, 0, 3, WEEKS, 2014, 2, 2},
            {2014, 0, 0, -5, WEEKS, 2014, 1, 35},
            {2014, 0, 0, 73 * 4, WEEKS, 2018, 0, 0},  // 4 full Discordian years lands on next St. Tib's Day
            // MONTHS from St. Tib's Day (adding months from the leap-day pseudo-month)
            {2014, 0, 0, 0, MONTHS, 2014, 0, 0},
            {2014, 0, 0, 3, MONTHS, 2014, 4, 60},
            {2014, 0, 0, -5, MONTHS, 2013, 1, 60},
            {2014, 0, 0, 20, MONTHS, 2018, 0, 0},  // 20 months = 4 years, lands back on St. Tib's Day
            // YEARS from St. Tib's Day
            {2014, 0, 0, 0, YEARS, 2014, 0, 0},
            {2014, 0, 0, 3, YEARS, 2017, 1, 60},   // 2017 is not a leap year, so moves to day 60
            {2014, 0, 0, -5, YEARS, 2009, 1, 60},  // 2009 is not a leap year
            {2014, 0, 0, 4, YEARS, 2018, 0, 0},    // 2018 is a leap year, stays on St. Tib's Day
        };
    }

    /**
     * Inputs and expected results for {@code minus()} when the result is St. Tib's Day.
     *
     * <p>Column layout: {startYear, startMonth, startDom, amount, unit, expectedYear, expectedMonth, expectedDom}
     *
     * <p>Each row represents: startDate.minus(amount, unit) == expectedDate (which is St. Tib's Day).
     */
    public static Object[][] data_minus_leap() {
        return new Object[][] {
            // DAYS to reach St. Tib's Day
            {2014, 0, 0, 0, DAYS, 2014, 0, 0},
            {2014, 1, 52, 8, DAYS, 2014, 0, 0},    // day 52 minus 8 days = St. Tib's Day (ordinal 60)
            {2014, 1, 62, -3, DAYS, 2014, 0, 0},   // day 62 minus -3 days (= plus 3) = St. Tib's Day
            // WEEKS to reach St. Tib's Day
            {2014, 0, 0, 0, WEEKS, 2014, 0, 0},
            {2014, 1, 45, 3, WEEKS, 2014, 0, 0},
            {2014, 2, 12, -5, WEEKS, 2014, 0, 0},
            {2010, 0, 0, 73 * 4, WEEKS, 2014, 0, 0},
            // MONTHS to reach St. Tib's Day
            {2014, 0, 0, 0, MONTHS, 2014, 0, 0},
            {2013, 3, 60, 3, MONTHS, 2014, 0, 0},
            {2015, 1, 60, -5, MONTHS, 2014, 0, 0},
            {2010, 0, 0, 20, MONTHS, 2014, 0, 0},
            // YEARS to reach St. Tib's Day
            {2014, 0, 0, 0, YEARS, 2014, 0, 0},
            {2011, 1, 60, 3, YEARS, 2014, 0, 0},
            {2019, 1, 60, -5, YEARS, 2014, 0, 0},
            {2010, 0, 0, 4, YEARS, 2014, 0, 0},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom), DiscordianDate.of(year, month, dom).plus(amount, unit));
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom), DiscordianDate.of(year, month, dom).plus(amount, unit));
    }

    /**
     * Verifies {@code minus} by reusing {@code data_plus} with column order reversed.
     *
     * <p>The data_plus table has columns: {year, month, dom, amount, unit, expectedYear, expectedMonth, expectedDom}.
     * This test reads them as: {expectedYear, expectedMonth, expectedDom, amount, unit, year, month, dom}.
     * That means: startingFrom(expectedDate).minus(amount, unit) == originalDate.
     * This confirms subtraction is the inverse of addition.
     */
    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit,
            int year, int month, int dom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom), DiscordianDate.of(year, month, dom).minus(amount, unit));
    }

    /**
     * Verifies {@code minus} for St. Tib's Day cases using {@code data_minus_leap}.
     *
     * <p>Column layout mirrors data_minus_leap: {startYear, startMonth, startDom, amount, unit, expectedYear, expectedMonth, expectedDom}.
     * Each row asserts: startDate.minus(amount, unit) == expectedDate (St. Tib's Day).
     */
    @ParameterizedTest
    @MethodSource("data_minus_leap")
    public void test_minus_leap_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit,
            int year, int month, int dom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom), DiscordianDate.of(year, month, dom).minus(amount, unit));
    }

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> DiscordianDate.of(2012, 5, 30).plus(0, MINUTES));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.until
    //-----------------------------------------------------------------------
    /**
     * Inputs and expected results for {@code DiscordianDate.until(Temporal, TemporalUnit)}.
     *
     * <p>Column layout: {year1, month1, dom1, year2, month2, dom2, unit, expected}
     *
     * <p>Covers: standard cases, St. Tib's Day boundaries, and leap-year edge cases where
     * the distance between a leap-day and a regular day is non-obvious.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // Standard cases without St. Tib's Day
            {2014, 5, 26, 2014, 5, 26, DAYS, 0},
            {2014, 5, 26, 2014, 5, 32, DAYS, 6},
            {2014, 5, 26, 2014, 5, 20, DAYS, -6},
            {2014, 5, 26, 2014, 5, 26, WEEKS, 0},
            {2014, 5, 26, 2014, 5, 30, WEEKS, 0},
            {2014, 5, 26, 2014, 5, 31, WEEKS, 1},
            {2014, 5, 26, 2014, 5, 26, MONTHS, 0},
            {2014, 5, 26, 2015, 1, 25, MONTHS, 0},
            {2014, 5, 26, 2015, 1, 26, MONTHS, 1},
            {2014, 5, 26, 2014, 5, 26, YEARS, 0},
            {2014, 5, 26, 2015, 5, 25, YEARS, 0},
            {2014, 5, 26, 2015, 5, 26, YEARS, 1},
            {2014, 5, 26, 2014, 5, 26, DECADES, 0},
            {2014, 5, 26, 2024, 5, 25, DECADES, 0},
            {2014, 5, 26, 2024, 5, 26, DECADES, 1},
            {2014, 5, 26, 2014, 5, 26, CENTURIES, 0},
            {2014, 5, 26, 2114, 5, 25, CENTURIES, 0},
            {2014, 5, 26, 2114, 5, 26, CENTURIES, 1},
            {2014, 5, 26, 2014, 5, 26, MILLENNIA, 0},
            {2014, 5, 26, 3014, 5, 25, MILLENNIA, 0},
            {2014, 5, 26, 3014, 5, 26, MILLENNIA, 1},
            {2013, 5, 26, 3014, 5, 26, ERAS, 0},  // only one era exists, always 0

            // Cases involving St. Tib's Day (month=0, day=0):
            // day 59 to day 60 of month 1 spans St. Tib's Day, so distance is 2 (not 1)
            {2014, 1, 59, 2014, 1, 60, DAYS, 2},
            {2014, 1, 59, 2014, 0, 0, DAYS, 1},    // day 59 to St. Tib's Day = 1 day
            {2014, 0, 0, 2014, 1, 60, DAYS, 1},    // St. Tib's Day to day 60 = 1 day
            {2014, 1, 60, 2014, 1, 55, DAYS, -6},  // going backward crosses St. Tib's Day

            // WEEKS around St. Tib's Day
            {2014, 0, 0, 2014, 0, 0, WEEKS, 0},
            {2014, 1, 60, 2014, 1, 60, WEEKS, 0},
            {2014, 1, 60, 2014, 1, 59, WEEKS, 0},
            {2014, 1, 60, 2014, 1, 56, WEEKS, 0},
            {2014, 1, 60, 2014, 1, 55, WEEKS, -1},
            {2014, 0, 0, 2014, 1, 54, WEEKS, -1},
            {2014, 0, 0, 2014, 1, 55, WEEKS, 0},
            {2014, 0, 0, 2014, 1, 64, WEEKS, 0},
            {2014, 0, 0, 2014, 1, 65, WEEKS, 1},
            {2014, 1, 54, 2014, 0, 0, WEEKS, 1},
            {2014, 1, 55, 2014, 0, 0, WEEKS, 0},
            {2014, 1, 64, 2014, 0, 0, WEEKS, 0},
            {2014, 1, 65, 2014, 0, 0, WEEKS, -1},

            // MONTHS around St. Tib's Day
            {2014, 0, 0, 2014, 0, 0, MONTHS, 0},
            {2014, 0, 0, 2014, 2, 59, MONTHS, 0},
            {2014, 0, 0, 2014, 2, 60, MONTHS, 1},
            {2014, 2, 60, 2014, 0, 0, MONTHS, -1},
            {2014, 2, 59, 2014, 0, 0, MONTHS, 0},
            {2013, 5, 59, 2014, 0, 0, MONTHS, 1},
            {2013, 5, 60, 2014, 0, 0, MONTHS, 0},
            {2013, 5, 60, 2014, 1, 60, MONTHS, 1},

            // YEARS around St. Tib's Day
            {2014, 0, 0, 2014, 0, 0, YEARS, 0},
            {2014, 0, 0, 2015, 1, 59, YEARS, 0},
            {2014, 0, 0, 2015, 1, 60, YEARS, 1},
            {2013, 1, 60, 2014, 0, 0, YEARS, 0},
            {2013, 1, 59, 2014, 0, 0, YEARS, 1},
            {2013, 1, 60, 2014, 1, 60, YEARS, 1},
            {2014, 0, 0, 2013, 1, 59, YEARS, -1},
            {2014, 0, 0, 2013, 1, 60, YEARS, 0},
            {2015, 1, 60, 2014, 0, 0, YEARS, -1},
            {2015, 1, 59, 2014, 0, 0, YEARS, 0},
            {2018, 0, 0, 2014, 0, 0, YEARS, -4},
            {2014, 0, 0, 2018, 0, 0, YEARS, 4},
        };
    }

    /**
     * Inputs and expected results for {@code DiscordianDate.until(ChronoLocalDate)} (period form).
     *
     * <p>Column layout: {year1, month1, dom1, year2, month2, dom2, periodYears, periodMonths, periodDays}
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Standard cases without St. Tib's Day
            {2014, 5, 26, 2014, 5, 26, 0, 0, 0},
            {2014, 5, 26, 2014, 5, 32, 0, 0, 6},
            {2014, 5, 26, 2014, 5, 20, 0, 0, -6},
            {2014, 5, 26, 2014, 5, 30, 0, 0, 4},
            {2014, 5, 26, 2014, 5, 31, 0, 0, 5},
            {2014, 5, 26, 2015, 1, 25, 0, 0, 72},
            {2014, 5, 26, 2015, 1, 26, 0, 1, 0},
            {2014, 5, 26, 2015, 5, 25, 0, 4, 72},
            {2014, 5, 26, 2015, 5, 26, 1, 0, 0},
            {2014, 5, 26, 2024, 5, 25, 9, 4, 72},
            {2014, 5, 26, 2024, 5, 26, 10, 0, 0},

            // Cases involving St. Tib's Day boundaries
            {2014, 1, 59, 2014, 1, 60, 0, 0, 2},   // crosses St. Tib's Day — gap is 2 days
            {2014, 1, 59, 2014, 0, 0, 0, 0, 1},
            {2014, 0, 0, 2014, 1, 60, 0, 0, 1},
            {2014, 1, 60, 2014, 1, 55, 0, 0, -6},
            {2014, 1, 60, 2014, 1, 59, 0, 0, -2},
            {2014, 1, 60, 2014, 1, 55, 0, 0, -6},
            {2014, 0, 0, 2014, 1, 54, 0, 0, -6},
            {2014, 0, 0, 2014, 1, 65, 0, 0, 6},
            {2014, 1, 55, 2014, 0, 0, 0, 0, 5},
            {2014, 1, 64, 2014, 0, 0, 0, 0, -5},
            {2014, 0, 0, 2014, 2, 59, 0, 0, 73},
            {2014, 0, 0, 2014, 2, 60, 0, 1, 0},
            {2014, 2, 60, 2014, 0, 0, 0, -1, -1},
            {2014, 2, 59, 2014, 0, 0, 0, 0, -73},
            {2013, 5, 59, 2014, 0, 0, 0, 1, 1},
            {2013, 5, 60, 2014, 0, 0, 0, 0, 73},
            {2013, 5, 60, 2014, 1, 60, 0, 1, 0},
            {2014, 0, 0, 2015, 1, 59, 0, 4, 72},
            {2014, 0, 0, 2015, 1, 60, 1, 0, 0},
            {2013, 1, 60, 2014, 0, 0, 0, 4, 73},
            {2013, 1, 59, 2014, 0, 0, 1, 0, 1},
            {2013, 1, 60, 2014, 1, 60, 1, 0, 0},
            {2014, 0, 0, 2013, 1, 59, -1, 0, -1},
            {2014, 0, 0, 2013, 1, 60, 0, -4, -73},
            {2015, 1, 60, 2014, 0, 0, -1, 0, -1},
            {2015, 1, 59, 2014, 0, 0, 0, -4, -73},
            {2018, 0, 0, 2014, 0, 0, -4, 0, 0},
            {2014, 0, 0, 2018, 0, 0, 4, 0, 0},
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        DiscordianDate start = DiscordianDate.of(year1, month1, dom1);
        DiscordianDate end = DiscordianDate.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int yearPeriod, int monthPeriod, int domPeriod) {
        DiscordianDate start = DiscordianDate.of(year1, month1, dom1);
        DiscordianDate end = DiscordianDate.of(year2, month2, dom2);
        ChronoPeriod period = DiscordianChronology.INSTANCE.period(yearPeriod, monthPeriod, domPeriod);
        assertEquals(period, start.until(end));
    }

    @Test
    public void test_until_TemporalUnit_unsupported() {
        DiscordianDate start = DiscordianDate.of(2012, 1, 30);
        DiscordianDate end = DiscordianDate.of(2012, 2, 1);
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.plus/minus with ChronoPeriod
    //-----------------------------------------------------------------------
    @Test
    public void test_plus_Period() {
        assertEquals(DiscordianDate.of(2015, 2, 29), DiscordianDate.of(2014, 5, 26).plus(DiscordianChronology.INSTANCE.period(0, 2, 3)));
    }

    @Test
    public void test_plus_Period_ISO() {
        // ISO Period cannot be applied to a Discordian date
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }

    @Test
    public void test_minus_Period() {
        assertEquals(DiscordianDate.of(2014, 3, 23), DiscordianDate.of(2014, 5, 26).minus(DiscordianChronology.INSTANCE.period(0, 2, 3)));
    }

    @Test
    public void test_minus_Period_ISO() {
        // ISO Period cannot be applied to a Discordian date
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(DiscordianDate.of(2000, 1, 3), DiscordianDate.of(2000, 1, 3))
            .addEqualityGroup(DiscordianDate.of(2000, 1, 4), DiscordianDate.of(2000, 1, 4))
            .addEqualityGroup(DiscordianDate.of(2000, 2, 3), DiscordianDate.of(2000, 2, 3))
            .addEqualityGroup(DiscordianDate.of(2001, 1, 3), DiscordianDate.of(2001, 1, 3))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    /**
     * Expected string representations for DiscordianDate.
     *
     * <p>St. Tib's Day uses a special textual format ("St. Tib's Day") instead of a month/day number.
     * Regular dates use the format "Discordian YOLD {year}-{month}-{day:02d}".
     *
     * <p>Column layout: {discordianDate, expectedString}
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            {DiscordianDate.of(1, 1, 1), "Discordian YOLD 1-1-01"},
            {DiscordianDate.of(2012, 5, 23), "Discordian YOLD 2012-5-23"},
            {DiscordianDate.of(2014, 0, 0), "Discordian YOLD 2014 St. Tib's Day"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(DiscordianDate discordian, String expected) {
        assertEquals(expected, discordian.toString());
    }

}
