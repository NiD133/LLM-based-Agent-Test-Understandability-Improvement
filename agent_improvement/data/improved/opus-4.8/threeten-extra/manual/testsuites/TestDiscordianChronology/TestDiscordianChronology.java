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
 * Tests for {@link DiscordianChronology} and its associated {@link DiscordianDate}.
 * <p>
 * Background needed to read this suite:
 * <ul>
 * <li>The Discordian year is offset from the ISO year by 1166: ISO year {@code N}
 *     corresponds to Discordian year {@code N + 1166}.</li>
 * <li>A Discordian year has 5 months of 73 days each (5 days per week, 73 weeks per year).</li>
 * <li>St. Tib's Day is a leap day that belongs to no month and no week. It is modelled as
 *     month 0, day 0 (written {@code DiscordianDate.of(year, 0, 0)}) and is inserted between
 *     the 59th and 60th day of the first month, mirroring the ISO February 29th.</li>
 * </ul>
 * Many tests are data-driven; each {@code data_*} provider documents the meaning of its
 * columns immediately above the data rows.
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
     * Pairs of equivalent dates: each row is {@code {discordianDate, equivalentIsoDate}}.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            {DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1)},
            {DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1)},
            {DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1)},
            {DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1)},

            {DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1)},
            {DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2)},
            {DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3)},

            {DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26)},
            {DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27)},
            {DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28)},
            {DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1)},

            {DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26)},
            {DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27)},
            {DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28)},
            // Leap year: St. Tib's Day (month 0, day 0) maps to ISO February 29th.
            {DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29)},
            {DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1)},

            {DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26)},
            {DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27)},
            {DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28)},
            {DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1)},
            {DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2)},

            {DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31)},
            {DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30)},

            {DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14)},
            {DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15)},
            {DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12)},

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
        // A date is always zero distance from itself.
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordian.until(discordian));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_until_LocalDate(DiscordianDate discordian, LocalDate iso) {
        // The Discordian date and its equivalent ISO date denote the same day, so the period is zero.
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordian.until(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        // The ISO date and its equivalent Discordian date denote the same day, so the period is zero.
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
        // Adding N days to the Discordian date must match adding N days to the equivalent ISO date.
        assertEquals(iso, LocalDate.from(discordian.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(discordian.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(discordian.plus(35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(discordian.plus(-1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(discordian.plus(-60, DAYS)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(DiscordianDate discordian, LocalDate iso) {
        // Subtracting N days from the Discordian date must match subtracting N days from the equivalent ISO date.
        assertEquals(iso, LocalDate.from(discordian.minus(0, DAYS)));
        assertEquals(iso.minusDays(1), LocalDate.from(discordian.minus(1, DAYS)));
        assertEquals(iso.minusDays(35), LocalDate.from(discordian.minus(35, DAYS)));
        assertEquals(iso.minusDays(-1), LocalDate.from(discordian.minus(-1, DAYS)));
        assertEquals(iso.minusDays(-60), LocalDate.from(discordian.minus(-60, DAYS)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(DiscordianDate discordian, LocalDate iso) {
        // The day-distance from the Discordian date to a shifted ISO date equals the shift amount.
        assertEquals(0, discordian.until(iso.plusDays(0), DAYS));
        assertEquals(1, discordian.until(iso.plusDays(1), DAYS));
        assertEquals(35, discordian.until(iso.plusDays(35), DAYS));
        assertEquals(-40, discordian.until(iso.minusDays(40), DAYS));
    }

    /**
     * Out-of-range {@code (year, month, dayOfMonth)} triples that must be rejected.
     * <p>
     * Valid Discordian fields are: month 1..5 (plus the special 0 for St. Tib's Day) and
     * day-of-month 1..73 (plus the special 0 for St. Tib's Day).
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            {1900, 0, 0},

            {1900, -1, 1},
            {1900, 0, 1},
            {1900, 6, 1},
            {1900, 7, 1},

            {1900, 1, -1},
            {1900, 1, 0},
            {1900, 1, 74},

            {1900, 0, 0},

            {1900, 5, -1},
            {1900, 5, 0},
            {1900, 5, 74},

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
        // 2001 is not a leap Discordian year, so day-of-year 366 does not exist.
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.dateYearDay(2001, 366));
    }

    //-----------------------------------------------------------------------
    // isLeapYear()
    //-----------------------------------------------------------------------
    @Test
    public void test_isLeapYear_loop() {
        // Discordian leap years follow the ISO rule applied to the ISO-equivalent year (proleptic year - 1166).
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
        // Leap years fall on 1162, 1166, 1170, 1174 (every 4 years), matching the ISO calendar.
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1174));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1173));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1172));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1171));
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1170));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1169));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1168));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1167));
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1166));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1165));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1164));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1163));
        assertTrue(DiscordianChronology.INSTANCE.isLeapYear(1162));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1161));
        assertFalse(DiscordianChronology.INSTANCE.isLeapYear(1160));
    }

    /**
     * Month-length cases: each row is {@code {year, month, expectedLengthOfMonth}}.
     * Every regular Discordian month has 73 days.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            {1900, 1, 73},
            {1900, 2, 73},
            {1900, 3, 73},
            {1900, 4, 73},
            {1900, 5, 73},

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
        // St. Tib's Day forms its own one-day "month"; regular months are 73 days long.
        assertEquals(1, DiscordianDate.of(3178, 0, 0).lengthOfMonth());
        assertEquals(73, DiscordianDate.of(3178, 1, 1).lengthOfMonth());
        assertEquals(73, DiscordianDate.of(3178, 1, 73).lengthOfMonth());
    }

    //-----------------------------------------------------------------------
    // era, prolepticYear and dateYearDay
    //-----------------------------------------------------------------------
    @Test
    public void test_era_loop() {
        // For every year, the proleptic year equals the year-of-era (there is only the YOLD era),
        // and building a date by era should match building it by proleptic year.
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
        // Same invariant as test_era_loop, but constructing dates via (year, day-of-year).
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
        // In the single YOLD era the proleptic year is identical to the year-of-era.
        assertEquals(4, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 4));
        assertEquals(3, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 3));
        assertEquals(2, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 2));
        assertEquals(1, DiscordianChronology.INSTANCE.prolepticYear(DiscordianEra.YOLD, 1));
    }

    @Test
    public void test_prolepticYear_badEra() {
        // An era from another calendar system is not accepted.
        assertThrows(ClassCastException.class, () -> DiscordianChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(DiscordianEra.YOLD, DiscordianChronology.INSTANCE.eraOf(1));
    }

    @Test
    public void test_Chronology_eraOf_invalid() {
        // YOLD (value 1) is the only valid era.
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
     * Per-date field ranges: each row is
     * {@code {year, month, dayOfMonth, field, expectedRangeMin, expectedRangeMax}}.
     * The inline comments explain how St. Tib's Day affects each field's range.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // St Tibs is in its own month, so (0 to 0) or (1 to 73)
            {2010, 0, 0, DAY_OF_MONTH, 0, 0},
            {2010, 1, 23, DAY_OF_MONTH, 1, 73},
            {2010, 2, 23, DAY_OF_MONTH, 1, 73},
            {2010, 3, 23, DAY_OF_MONTH, 1, 73},
            {2010, 4, 23, DAY_OF_MONTH, 1, 73},
            {2010, 5, 23, DAY_OF_MONTH, 1, 73},
            // Day of year is an ordinal count including St Tibs
            {2010, 0, 0, DAY_OF_YEAR, 1, 366},
            {2010, 1, 23, DAY_OF_YEAR, 1, 366},
            {2011, 2, 23, DAY_OF_YEAR, 1, 365},
            // St Tibs is still in same year, so (0 to 5) in leap year and (1 to 5) in non-leap year
            {2010, 0, 0, MONTH_OF_YEAR, 0, 5},
            {2010, 1, 1, MONTH_OF_YEAR, 0, 5},
            {2011, 1, 23, MONTH_OF_YEAR, 1, 5},
            // St Tibs is in its own month, so (0 to 0) or (1 to 5)
            {2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 0},
            {2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5},
            {2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5},
            {2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5},
            // St Tibs is still in same year, so (0 to 5) in leap year and (1 to 5) in non-leap year
            {2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5},
            {2011, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 5},
            // St Tibs is in its own month, so (0 to 0) or (1 to 15)
            {2010, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 0},
            {2010, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 15},
            // St Tibs is still in same year, so (0 to 73) in leap year and (1 to 73) in non-leap year
            {2010, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 73},
            {2010, 1, 23, ALIGNED_WEEK_OF_YEAR, 0, 73},
            {2011, 1, 23, ALIGNED_WEEK_OF_YEAR, 1, 73},
            // St Tibs is in its own week, so (0 to 0) or (1 to 5)
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
        // A time-based field has no range on a date.
        assertThrows(UnsupportedTemporalTypeException.class, () -> DiscordianDate.of(2012, 5, 30).range(MINUTE_OF_DAY));
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.getLong
    //-----------------------------------------------------------------------
    /**
     * Field-value cases: each row is {@code {year, month, dayOfMonth, field, expectedFieldValue}}.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            {2014, 1, 26, DAY_OF_WEEK, 1},
            {2014, 1, 26, DAY_OF_MONTH, 26},
            {2014, 1, 26, DAY_OF_YEAR, 26},
            {2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1},
            {2014, 1, 26, ALIGNED_WEEK_OF_MONTH, 6},
            {2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1},
            {2014, 1, 26, ALIGNED_WEEK_OF_YEAR, 6},
            {2014, 1, 26, MONTH_OF_YEAR, 1},
            {2014, 1, 26, PROLEPTIC_MONTH, 2014 * 5 + 1 - 1},
            {2014, 1, 26, YEAR, 2014},
            {2014, 1, 26, ERA, 1},

            {2014, 5, 26, DAY_OF_WEEK, 3},
            {2014, 5, 26, DAY_OF_MONTH, 26},
            // Day-of-year accumulates four full 73-day months plus 26 days into the fifth month.
            {2014, 5, 26, DAY_OF_YEAR, 1 + 73 + 73 + 73 + 73 + 26},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 6},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 64},
            {2014, 5, 26, MONTH_OF_YEAR, 5},
            {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1},
            {2014, 5, 26, YEAR, 2014},
            {2014, 5, 26, ERA, 1},
            {1, 5, 8, ERA, 1},

            // St. Tib's Day (month 0, day 0): most fields read 0, but it is day-of-year 60.
            {2014, 0, 0, DAY_OF_WEEK, 0},
            {2014, 0, 0, DAY_OF_MONTH, 0},
            {2014, 0, 0, DAY_OF_YEAR, 60},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
            {2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 0},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
            {2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 0},
            {2014, 0, 0, MONTH_OF_YEAR, 0},
            {2014, 0, 0, PROLEPTIC_MONTH, 2014 * 5 + 1 - 1},
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
     * {@code with(field, value)} cases. Each row is
     * {@code {year, month, dayOfMonth, field, newValue, expectedYear, expectedMonth, expectedDayOfMonth}}:
     * setting {@code field} to {@code newValue} on the start date yields the expected date.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            {2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 24},
            {2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 26},
            {2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31},
            {2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26},
            {2014, 5, 26, DAY_OF_YEAR, 365, 2014, 5, 72},
            {2014, 5, 26, DAY_OF_YEAR, 319, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 28},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 1},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 6, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 25},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 2, 40},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 64, 2014, 5, 26},
            {2014, 5, 26, MONTH_OF_YEAR, 4, 2014, 4, 26},
            {2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26},
            {2014, 5, 26, PROLEPTIC_MONTH, 2013 * 5 + 3 - 1, 2013, 3, 26},
            {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1, 2014, 5, 26},
            {2014, 5, 26, YEAR, 2012, 2012, 5, 26},
            {2014, 5, 26, YEAR, 2014, 2014, 5, 26},
            {2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26},
            {2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26},
            {2014, 5, 26, ERA, 1, 2014, 5, 26},

            // Starting from St. Tib's Day (month 0, day 0): adjusting a within-week/month/year field
            // moves into the surrounding first month near day 56-60.
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 2014, 0, 0},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 1, 56},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2014, 1, 57},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 1, 58},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2014, 1, 59},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 1, 60},

            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 2014, 0, 0},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2014, 1, 56},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 1, 57},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 1, 58},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2014, 1, 59},
            {2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 1, 60},

            {2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 2014, 0, 0},
            {2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 3, 2014, 1, 15},

            {2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 2014, 0, 0},
            {2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 3, 2014, 1, 15},

            {2014, 0, 0, DAY_OF_WEEK, 0, 2014, 0, 0},
            {2014, 0, 0, DAY_OF_WEEK, 1, 2014, 1, 56},
            {2014, 0, 0, DAY_OF_WEEK, 2, 2014, 1, 57},
            {2014, 0, 0, DAY_OF_WEEK, 3, 2014, 1, 58},
            {2014, 0, 0, DAY_OF_WEEK, 4, 2014, 1, 59},
            {2014, 0, 0, DAY_OF_WEEK, 5, 2014, 1, 60},

            {2014, 0, 0, DAY_OF_MONTH, 0, 2014, 0, 0},
            {2014, 0, 0, DAY_OF_MONTH, 3, 2014, 1, 3},

            {2014, 0, 0, MONTH_OF_YEAR, 0, 2014, 0, 0},
            {2014, 0, 0, MONTH_OF_YEAR, 1, 2014, 1, 60},
            {2014, 0, 0, MONTH_OF_YEAR, 2, 2014, 2, 60},

            {2014, 0, 0, YEAR, 2014, 2014, 0, 0},
            {2014, 0, 0, YEAR, 2013, 2013, 1, 60},
            {2014, 0, 0, YEAR, 2015, 2015, 1, 60},
            {2014, 0, 0, YEAR, 2018, 2018, 0, 0},

            {2014, 3, 31, DAY_OF_MONTH, 0, 2014, 0, 0},
            {2014, 1, 31, DAY_OF_MONTH, 0, 2014, 0, 0},
            {2014, 3, 31, MONTH_OF_YEAR, 0, 2014, 0, 0},
            {2014, 3, 31, DAY_OF_YEAR, 60, 2014, 0, 0},
            {2013, 3, 31, DAY_OF_YEAR, 60, 2013, 1, 60},
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
     * {@code with(field, value)} cases that must be rejected. Each row is
     * {@code {year, month, dayOfMonth, field, invalidValue}}.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            {2013, 1, 1, DAY_OF_WEEK, 0},
            {2013, 1, 1, DAY_OF_WEEK, 6},
            {2014, 1, 1, DAY_OF_WEEK, -1},
            {2014, 1, 1, DAY_OF_WEEK, 6},

            {2013, 1, 1, DAY_OF_MONTH, 0},
            {2013, 1, 1, DAY_OF_MONTH, 74},
            {2014, 1, 1, DAY_OF_MONTH, -1},
            {2014, 1, 1, DAY_OF_MONTH, 74},

            {2013, 1, 1, DAY_OF_YEAR, 0},
            {2014, 1, 1, DAY_OF_YEAR, 0},
            {2013, 1, 1, DAY_OF_YEAR, 367},
            {2014, 1, 1, DAY_OF_YEAR, 367},

            {2013, 1, 1, MONTH_OF_YEAR, 0},
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
        // St. Tib's Day is a one-day month, so "last day of month" leaves it unchanged.
        DiscordianDate base = DiscordianDate.of(2014, 0, 0);
        DiscordianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(DiscordianDate.of(2014, 0, 0), test);
    }

    @Test
    public void test_adjust2() {
        // The last day of a regular month is day 73.
        DiscordianDate base = DiscordianDate.of(2012, 2, 23);
        DiscordianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(DiscordianDate.of(2012, 2, 73), test);
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.with(Local*)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjust_toLocalDate() {
        // Adjusting a Discordian date with an ISO date sets it to the equivalent Discordian date.
        DiscordianDate discordian = DiscordianDate.of(2000, 1, 4);
        DiscordianDate test = discordian.with(LocalDate.of(2012, 7, 6));
        assertEquals(DiscordianDate.of(3178, 3, 41), test);
    }

    @Test
    public void test_adjust_toMonth() {
        // An ISO Month cannot adjust a Discordian date (incompatible month numbering).
        DiscordianDate discordian = DiscordianDate.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> discordian.with(Month.APRIL));
    }

    //-----------------------------------------------------------------------
    // LocalDate.with(DiscordianDate)
    //-----------------------------------------------------------------------
    @Test
    public void test_LocalDate_adjustToDiscordianDate() {
        // Adjusting an ISO date with a Discordian date sets it to the equivalent ISO date.
        DiscordianDate discordian = DiscordianDate.of(3178, 3, 41);
        LocalDate test = LocalDate.MIN.with(discordian);
        assertEquals(LocalDate.of(2012, 7, 6), test);
    }

    @Test
    public void test_LocalDateTime_adjustToDiscordianDate() {
        // Same as above, preserving the (midnight) time component of the ISO date-time.
        DiscordianDate discordian = DiscordianDate.of(3178, 3, 41);
        LocalDateTime test = LocalDateTime.MIN.with(discordian);
        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), test);
    }

    //-----------------------------------------------------------------------
    // DiscordianDate.plus
    //-----------------------------------------------------------------------
    /**
     * {@code plus(amount, unit)} cases on regular (non-leap) dates. Each row is
     * {@code {year, month, dayOfMonth, amount, unit, expectedYear, expectedMonth, expectedDayOfMonth}}.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            {2014, 5, 26, 0, DAYS, 2014, 5, 26},
            {2014, 5, 26, 8, DAYS, 2014, 5, 34},
            {2014, 5, 26, -3, DAYS, 2014, 5, 23},
            {2014, 5, 26, 0, WEEKS, 2014, 5, 26},
            {2014, 5, 26, 3, WEEKS, 2014, 5, 41},
            {2014, 5, 26, -5, WEEKS, 2014, 5, 1},
            {2014, 5, 26, 0, MONTHS, 2014, 5, 26},
            {2014, 5, 26, 3, MONTHS, 2015, 3, 26},
            {2014, 5, 26, -5, MONTHS, 2013, 5, 26},
            {2014, 5, 26, 0, YEARS, 2014, 5, 26},
            {2014, 5, 26, 3, YEARS, 2017, 5, 26},
            {2014, 5, 26, -5, YEARS, 2009, 5, 26},
            {2014, 5, 26, 0, DECADES, 2014, 5, 26},
            {2014, 5, 26, 3, DECADES, 2044, 5, 26},
            {2014, 5, 26, -5, DECADES, 1964, 5, 26},
            {2014, 5, 26, 0, CENTURIES, 2014, 5, 26},
            {2014, 5, 26, 3, CENTURIES, 2314, 5, 26},
            {2014, 5, 26, -5, CENTURIES, 1514, 5, 26},
            {2014, 5, 26, 0, MILLENNIA, 2014, 5, 26},
            {2014, 5, 26, 3, MILLENNIA, 5014, 5, 26},
            {2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26},
        };
    }

    /**
     * {@code plus(amount, unit)} cases that start on, or cross, St. Tib's Day. Same column layout
     * as {@link #data_plus()}: {@code {year, month, dayOfMonth, amount, unit, expYear, expMonth, expDom}}.
     */
    public static Object[][] data_plus_leap() {
        return new Object[][] {
            {2014, 0, 0, 0, DAYS, 2014, 0, 0},
            {2014, 0, 0, 8, DAYS, 2014, 1, 67},
            {2014, 0, 0, -3, DAYS, 2014, 1, 57},
            {2014, 0, 0, 0, WEEKS, 2014, 0, 0},
            {2014, 0, 0, 3, WEEKS, 2014, 2, 2},
            {2014, 0, 0, -5, WEEKS, 2014, 1, 35},
            {2014, 0, 0, 73 * 4, WEEKS, 2018, 0, 0},
            {2014, 0, 0, 0, MONTHS, 2014, 0, 0},
            {2014, 0, 0, 3, MONTHS, 2014, 4, 60},
            {2014, 0, 0, -5, MONTHS, 2013, 1, 60},
            {2014, 0, 0, 20, MONTHS, 2018, 0, 0},
            {2014, 0, 0, 0, YEARS, 2014, 0, 0},
            {2014, 0, 0, 3, YEARS, 2017, 1, 60},
            {2014, 0, 0, -5, YEARS, 2009, 1, 60},
            {2014, 0, 0, 4, YEARS, 2018, 0, 0},
        };
    }

    /**
     * {@code minus(amount, unit)} cases that land on St. Tib's Day. Each row is
     * {@code {year, month, dayOfMonth, amount, unit, expectedYear, expectedMonth, expectedDayOfMonth}}:
     * subtracting {@code amount} {@code unit}s from the start date yields the expected (leap-day) date.
     */
    public static Object[][] data_minus_leap() {
        return new Object[][] {
            {2014, 0, 0, 0, DAYS, 2014, 0, 0},
            {2014, 1, 52, 8, DAYS, 2014, 0, 0},
            {2014, 1, 62, -3, DAYS, 2014, 0, 0},
            {2014, 0, 0, 0, WEEKS, 2014, 0, 0},
            {2014, 1, 45, 3, WEEKS, 2014, 0, 0},
            {2014, 2, 12, -5, WEEKS, 2014, 0, 0},
            {2010, 0, 0, 73 * 4, WEEKS, 2014, 0, 0},
            {2014, 0, 0, 0, MONTHS, 2014, 0, 0},
            {2013, 3, 60, 3, MONTHS, 2014, 0, 0},
            {2015, 1, 60, -5, MONTHS, 2014, 0, 0},
            {2010, 0, 0, 20, MONTHS, 2014, 0, 0},
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
     * Reuses {@link #data_plus()} read backwards: {@code minus} is the inverse of {@code plus}, so
     * the provider's "expected" columns become this test's start date and its "start" columns become
     * the expected result. Hence the parameter order here is the mirror of {@link #test_plus_TemporalUnit}.
     */
    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit,
            int year, int month, int dom) {
        assertEquals(DiscordianDate.of(expectedYear, expectedMonth, expectedDom), DiscordianDate.of(year, month, dom).minus(amount, unit));
    }

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
     * {@code until(end, unit)} cases. Each row is
     * {@code {startYear, startMonth, startDom, endYear, endMonth, endDom, unit, expectedAmount}}:
     * the distance from the start date to the end date, measured in {@code unit}s.
     */
    public static Object[][] data_until() {
        return new Object[][] {
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
            {2013, 5, 26, 3014, 5, 26, ERAS, 0},

            // Cases involving St. Tib's Day (month 0, day 0) as start and/or end.
            {2014, 1, 59, 2014, 1, 60, DAYS, 2},
            {2014, 1, 59, 2014, 0, 0, DAYS, 1},
            {2014, 0, 0, 2014, 1, 60, DAYS, 1},
            {2014, 1, 60, 2014, 1, 55, DAYS, -6},
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
            {2014, 0, 0, 2014, 0, 0, MONTHS, 0},
            {2014, 0, 0, 2014, 2, 59, MONTHS, 0},
            {2014, 0, 0, 2014, 2, 60, MONTHS, 1},
            {2014, 2, 60, 2014, 0, 0, MONTHS, -1},
            {2014, 2, 59, 2014, 0, 0, MONTHS, 0},
            {2013, 5, 59, 2014, 0, 0, MONTHS, 1},
            {2013, 5, 60, 2014, 0, 0, MONTHS, 0},
            {2013, 5, 60, 2014, 1, 60, MONTHS, 1},
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
     * {@code until(end)} cases that return a {@link ChronoPeriod}. Each row is
     * {@code {startYear, startMonth, startDom, endYear, endMonth, endDom, periodYears, periodMonths, periodDays}}.
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
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

            // Cases involving St. Tib's Day (month 0, day 0) as start and/or end.
            {2014, 1, 59, 2014, 1, 60, 0, 0, 2},
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
    // DiscordianDate.plus(ChronoPeriod) / minus(ChronoPeriod)
    //-----------------------------------------------------------------------
    @Test
    public void test_plus_Period() {
        assertEquals(DiscordianDate.of(2015, 2, 29), DiscordianDate.of(2014, 5, 26).plus(DiscordianChronology.INSTANCE.period(0, 2, 3)));
    }

    @Test
    public void test_plus_Period_ISO() {
        // An ISO Period cannot be added to a Discordian date.
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }

    @Test
    public void test_minus_Period() {
        assertEquals(DiscordianDate.of(2014, 3, 23), DiscordianDate.of(2014, 5, 26).minus(DiscordianChronology.INSTANCE.period(0, 2, 3)));
    }

    @Test
    public void test_minus_Period_ISO() {
        // An ISO Period cannot be subtracted from a Discordian date.
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        // Dates differing in any of year, month, or day-of-month must not be equal.
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
     * {@code toString()} cases: each row is {@code {discordianDate, expectedText}}.
     * Note that St. Tib's Day renders specially rather than as a numeric month-day.
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
