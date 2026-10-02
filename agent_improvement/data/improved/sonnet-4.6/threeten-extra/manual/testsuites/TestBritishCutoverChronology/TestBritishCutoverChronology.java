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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link BritishCutoverChronology} and {@link BritishCutoverDate}.
 *
 * <p>Britain switched from the Julian calendar to the Gregorian calendar in September 1752.
 * Wednesday 2nd September 1752 (Julian) was immediately followed by Thursday 14th September 1752
 * (Gregorian), skipping 11 days. The dates 3rd through 13th September 1752 never existed in
 * British history.
 *
 * <p>Key rules verified by these tests:
 * <ul>
 *   <li><b>Before the cutover (up to 2 Sep 1752):</b> Julian calendar — leap year every 4 years
 *       with no century-year exception, meaning 1700 is a leap year in this calendar.
 *   <li><b>From the cutover (14 Sep 1752 onwards):</b> Gregorian calendar — century years not
 *       divisible by 400 are not leap years (e.g. 1800, 1900 are not leap years).
 *   <li><b>The cutover gap (3–13 Sep 1752):</b> Dates in this range are treated leniently as
 *       Julian dates and shifted forward by 11 days to produce a valid Gregorian date.
 *   <li><b>Year 1752:</b> Has only 355 days because 11 days were removed in September.
 * </ul>
 */
public class TestBritishCutoverChronology {

    // The ISO (Gregorian) LocalDate corresponding to the first day after the British cutover gap.
    // In British calendar terms this is also 14 September 1752.
    private static final LocalDate CUTOVER_ISO_DATE = LocalDate.of(1752, 9, 14);

    //-----------------------------------------------------------------------
    // Chronology.of(String)
    //-----------------------------------------------------------------------
    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("BritishCutover");
        assertNotNull(chrono);
        assertEquals(BritishCutoverChronology.INSTANCE, chrono);
        assertEquals("BritishCutover", chrono.getId());
        // No CLDR calendar type is defined for this calendar system
        assertNull(chrono.getCalendarType());
    }

    //-----------------------------------------------------------------------
    // creation, toLocalDate()
    //-----------------------------------------------------------------------
    /**
     * Pairs of (BritishCutoverDate, equivalent ISO LocalDate) spanning ancient dates,
     * the Vatican's 1582 cutover reference point, and the actual British 1752 cutover.
     *
     * <p>Column layout: [BritishCutoverDate, LocalDate (ISO/Gregorian equivalent)]
     *
     * <p>Before 1752, Julian dates differ from ISO dates by an increasing offset due to
     * accumulated leap year differences between the two calendar systems.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early AD dates — Julian calendar, offset from ISO is 2 days behind
            {BritishCutoverDate.of(1, 1, 1), LocalDate.of(0, 12, 30)},
            {BritishCutoverDate.of(1, 1, 2), LocalDate.of(0, 12, 31)},
            {BritishCutoverDate.of(1, 1, 3), LocalDate.of(1, 1, 1)},

            {BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26)},
            {BritishCutoverDate.of(1, 3, 1), LocalDate.of(1, 2, 27)},
            {BritishCutoverDate.of(1, 3, 2), LocalDate.of(1, 2, 28)},
            {BritishCutoverDate.of(1, 3, 3), LocalDate.of(1, 3, 1)},

            // Year 4 — Julian has a leap day here (ISO also does), offset stays at 2
            {BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26)},
            {BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27)},
            {BritishCutoverDate.of(4, 3, 1), LocalDate.of(4, 2, 28)},
            {BritishCutoverDate.of(4, 3, 2), LocalDate.of(4, 2, 29)},
            {BritishCutoverDate.of(4, 3, 3), LocalDate.of(4, 3, 1)},

            // Year 100 — Julian has a leap day, ISO does not; offset increases by 1 after Feb
            {BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26)},
            {BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27)},
            {BritishCutoverDate.of(100, 3, 1), LocalDate.of(100, 2, 28)},
            {BritishCutoverDate.of(100, 3, 2), LocalDate.of(100, 3, 1)},
            {BritishCutoverDate.of(100, 3, 3), LocalDate.of(100, 3, 2)},

            {BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29)},
            {BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28)},

            // Around the Vatican's 1582 Gregorian adoption — offset at this point is 10 days
            {BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14)},
            {BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15)},

            // Pre-cutover dates in the year Britain switched — offset is 11 days
            {BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31)},
            {BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11)},
            {BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12)},
            // The last valid Julian date before the gap
            {BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12)},
            {BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13)},
            // Dates in the gap (3–13 Sep 1752) are leniently shifted forward by 11 days
            {BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14)},   // leniently accept invalid
            {BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24)},  // leniently accept invalid
            // The first valid Gregorian date after the gap: same in both calendars
            {BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14)},

            // Post-cutover modern dates — ISO and BritishCutover agree
            {BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12)},
            {BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5)},
            {BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_BritishCutoverDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso, LocalDate.from(cutover));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_from_LocalDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverDate.from(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_toEpochDay(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso.toEpochDay(), cutover.toEpochDay());
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_until_BritishCutoverDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(BritishCutoverChronology.INSTANCE.period(0, 0, 0), cutover.until(cutover));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_until_LocalDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(BritishCutoverChronology.INSTANCE.period(0, 0, 0), cutover.until(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_BritishCutoverDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(cutover));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverChronology.INSTANCE.date(iso));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso, LocalDate.from(cutover.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(cutover.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(cutover.plus(35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(cutover.plus(-1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(cutover.plus(-60, DAYS)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_minusDays(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso, LocalDate.from(cutover.minus(0, DAYS)));
        assertEquals(iso.minusDays(1), LocalDate.from(cutover.minus(1, DAYS)));
        assertEquals(iso.minusDays(35), LocalDate.from(cutover.minus(35, DAYS)));
        assertEquals(iso.minusDays(-1), LocalDate.from(cutover.minus(-1, DAYS)));
        assertEquals(iso.minusDays(-60), LocalDate.from(cutover.minus(-60, DAYS)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(0, cutover.until(iso.plusDays(0), DAYS));
        assertEquals(1, cutover.until(iso.plusDays(1), DAYS));
        assertEquals(35, cutover.until(iso.plusDays(35), DAYS));
        assertEquals(-40, cutover.until(iso.minusDays(40), DAYS));
    }

    /**
     * Invalid year/month/day combinations that must throw {@link DateTimeException}.
     *
     * <p>Column layout: [year, month, dayOfMonth]
     *
     * <p>Covers: month out of range (0, negative, >12), day out of range for various
     * months (including February in leap vs non-leap years), and day=0 or negative.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            {1900, 0, 0},

            // Month out of range
            {1900, -1, 1},
            {1900, 0, 1},
            {1900, 13, 1},
            {1900, 14, 1},

            // January: valid days are 1–31
            {1900, 1, -1},
            {1900, 1, 0},
            {1900, 1, 32},

            // February in a Gregorian non-leap century year (1900): valid days are 1–28
            {1900, 2, -1},
            {1900, 2, 0},
            {1900, 2, 30},
            {1900, 2, 31},
            {1900, 2, 32},

            // February in 1899 (not a leap year): valid days are 1–28
            {1899, 2, -1},
            {1899, 2, 0},
            {1899, 2, 29},
            {1899, 2, 30},
            {1899, 2, 31},
            {1899, 2, 32},

            // December: valid days are 1–31
            {1900, 12, -1},
            {1900, 12, 0},
            {1900, 12, 32},

            // Other months: each month's maximum exceeded
            {1900, 3, 32},
            {1900, 4, 31},
            {1900, 5, 32},
            {1900, 6, 31},
            {1900, 7, 32},
            {1900, 8, 32},
            {1900, 9, 31},
            {1900, 10, 32},
            {1900, 11, 31},
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> BritishCutoverDate.of(year, month, dom));
    }

    @Test
    public void test_Chronology_dateYearDay_badDate() {
        // Year 2001 is a regular Gregorian year with 365 days; day 366 is out of range
        assertThrows(DateTimeException.class, () -> BritishCutoverChronology.INSTANCE.dateYearDay(2001, 366));
    }

    //-----------------------------------------------------------------------
    // isLeapYear()
    //-----------------------------------------------------------------------
    @Test
    public void test_Chronology_isLeapYear_loop() {
        // Before and around the cutover year, only the Julian rule applies:
        // every year divisible by 4 is a leap year, with no century-year exception.
        for (int year = -200; year < 200; year++) {
            BritishCutoverDate base = BritishCutoverDate.of(year, 1, 1);
            assertEquals((year % 4) == 0, base.isLeapYear());
            assertEquals((year % 4) == 0, BritishCutoverChronology.INSTANCE.isLeapYear(year));
        }
    }

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // Julian leap year rule: every 4 years without exception
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(8));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(7));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(6));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(5));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(4));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(3));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(2));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(1));
        // Year 0 (1 BC) is divisible by 4 under the Julian rule
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(0));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-1));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-2));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-3));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(-4));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-5));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-6));
    }

    //-----------------------------------------------------------------------
    // getCutover()
    //-----------------------------------------------------------------------
    @Test
    public void test_Chronology_getCutover() {
        // The cutover is the first Gregorian date: Thursday 14 September 1752
        assertEquals(CUTOVER_ISO_DATE, BritishCutoverChronology.INSTANCE.getCutover());
    }

    //-----------------------------------------------------------------------
    // lengthOfMonth()
    //-----------------------------------------------------------------------
    /**
     * Expected month lengths for a variety of years and months.
     *
     * <p>Column layout: [year, month, expectedLengthInDays]
     *
     * <p>Notable cases:
     * <ul>
     *   <li>1700 — Julian leap year (Feb has 29 days), not a Gregorian leap year
     *   <li>1751 — normal Julian year (Feb has 28 days)
     *   <li>1752 September — only 19 days long due to the 11-day cutover gap (days 3–13 skipped)
     *   <li>1800, 1900 — Gregorian non-leap century years (Feb has 28 days)
     * </ul>
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // 1700: Julian leap year (divisible by 4, no century exception in Julian)
            {1700, 1, 31},
            {1700, 2, 29},  // leap year under Julian rules
            {1700, 3, 31},
            {1700, 4, 30},
            {1700, 5, 31},
            {1700, 6, 30},
            {1700, 7, 31},
            {1700, 8, 31},
            {1700, 9, 30},
            {1700, 10, 31},
            {1700, 11, 30},
            {1700, 12, 31},

            // 1751: standard Julian year (not divisible by 4)
            {1751, 1, 31},
            {1751, 2, 28},
            {1751, 3, 31},
            {1751, 4, 30},
            {1751, 5, 31},
            {1751, 6, 30},
            {1751, 7, 31},
            {1751, 8, 31},
            {1751, 9, 30},
            {1751, 10, 31},
            {1751, 11, 30},
            {1751, 12, 31},

            // 1752: the cutover year — September has only 19 days (days 3–13 were removed)
            {1752, 1, 31},
            {1752, 2, 29},  // Julian leap year
            {1752, 3, 31},
            {1752, 4, 30},
            {1752, 5, 31},
            {1752, 6, 30},
            {1752, 7, 31},
            {1752, 8, 31},
            {1752, 9, 19},  // 30 - 11 skipped days = 19
            {1752, 10, 31},
            {1752, 11, 30},
            {1752, 12, 31},

            // 1753: first full Gregorian year
            {1753, 1, 31},
            {1753, 3, 31},
            {1753, 2, 28},
            {1753, 4, 30},
            {1753, 5, 31},
            {1753, 6, 30},
            {1753, 7, 31},
            {1753, 8, 31},
            {1753, 9, 30},
            {1753, 10, 31},
            {1753, 11, 30},
            {1753, 12, 31},

            // February lengths for various Julian and Gregorian leap/non-leap years
            {1500, 2, 29},  // Julian leap year
            {1600, 2, 29},  // Julian leap year
            {1700, 2, 29},  // Julian leap year (not a Gregorian leap year)
            {1800, 2, 28},  // Gregorian non-leap century year
            {1900, 2, 28},  // Gregorian non-leap century year
            {1901, 2, 28},
            {1902, 2, 28},
            {1903, 2, 28},
            {1904, 2, 29},  // Gregorian leap year
            {2000, 2, 29},  // Gregorian leap year (divisible by 400)
            {2100, 2, 28},  // Gregorian non-leap century year
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, BritishCutoverDate.of(year, month, 1).lengthOfMonth());
    }

    //-----------------------------------------------------------------------
    // lengthOfYear()
    //-----------------------------------------------------------------------
    /**
     * Expected year lengths for a variety of years.
     *
     * <p>Column layout: [year, expectedLengthInDays]
     *
     * <p>Notable cases:
     * <ul>
     *   <li>1752 — only 355 days because 11 days were removed in September
     *   <li>Before 1753 — Julian leap year rule (every 4 years, no century exception),
     *       so 100, 1600, 1700 are all 366-day years
     *   <li>From 1753 — Gregorian leap year rule applies
     * </ul>
     */
    public static Object[][] data_lengthOfYear() {
        return new Object[][] {
            {-101, 365},
            {-100, 366},  // Julian leap year (divisible by 4)
            {-99, 365},
            {-1, 365},
            {0, 366},     // Julian leap year (year 0 = 1 BC, divisible by 4)
            {100, 366},   // Julian leap year (century year, but no Julian exception)
            {1600, 366},  // Julian leap year
            {1700, 366},  // Julian leap year (1700 is NOT a leap year in Gregorian)
            {1751, 365},
            {1748, 366},  // Julian leap year
            {1749, 365},
            {1750, 365},
            {1751, 365},
            {1752, 355},  // Cutover year: 366 Julian days minus 11 removed days
            {1753, 365},  // First full Gregorian year
            {1500, 366},  // Julian leap year
            {1600, 366},  // Julian leap year
            {1700, 366},  // Julian leap year
            {1800, 365},  // Gregorian non-leap century year
            {1900, 365},  // Gregorian non-leap century year
            {1901, 365},
            {1902, 365},
            {1903, 365},
            {1904, 366},  // Gregorian leap year
            {2000, 366},  // Gregorian leap year (divisible by 400)
            {2100, 365},  // Gregorian non-leap century year
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfYear")
    public void test_lengthOfYear_atStart(int year, int length) {
        assertEquals(length, BritishCutoverDate.of(year, 1, 1).lengthOfYear());
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfYear")
    public void test_lengthOfYear_atEnd(int year, int length) {
        assertEquals(length, BritishCutoverDate.of(year, 12, 31).lengthOfYear());
    }

    //-----------------------------------------------------------------------
    // era, prolepticYear and dateYearDay
    //-----------------------------------------------------------------------
    @Test
    public void test_era_loop() {
        for (int year = -200; year < 200; year++) {
            BritishCutoverDate base = BritishCutoverChronology.INSTANCE.date(year, 1, 1);
            assertEquals(year, base.get(YEAR));
            JulianEra era = (year <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(era, base.getEra());
            int yoe = (year <= 0 ? 1 - year : year);
            assertEquals(yoe, base.get(YEAR_OF_ERA));
            BritishCutoverDate eraBased = BritishCutoverChronology.INSTANCE.date(era, yoe, 1, 1);
            assertEquals(base, eraBased);
        }
    }

    @Test
    public void test_era_yearDay_loop() {
        for (int year = -200; year < 200; year++) {
            BritishCutoverDate base = BritishCutoverChronology.INSTANCE.dateYearDay(year, 1);
            assertEquals(year, base.get(YEAR));
            JulianEra era = (year <= 0 ? JulianEra.BC : JulianEra.AD);
            assertEquals(era, base.getEra());
            int yoe = (year <= 0 ? 1 - year : year);
            assertEquals(yoe, base.get(YEAR_OF_ERA));
            BritishCutoverDate eraBased = BritishCutoverChronology.INSTANCE.dateYearDay(era, yoe, 1);
            assertEquals(base, eraBased);
        }
    }

    @Test
    public void test_era_yearDay() {
        // Verify day-of-year to date mapping in 1752, which has only 355 days.
        // Days 1–246 fall in the Julian part (Jan 1 – Sep 2); day 247 is the first
        // Gregorian day (Sep 14); days after 257 are normal Gregorian dates.
        assertEquals(BritishCutoverDate.of(1752, 1, 1), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 1));
        assertEquals(BritishCutoverDate.of(1752, 8, 31), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 244));
        assertEquals(BritishCutoverDate.of(1752, 9, 2), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 246));
        assertEquals(BritishCutoverDate.of(1752, 9, 14), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 247)); // first Gregorian day
        assertEquals(BritishCutoverDate.of(1752, 9, 24), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 257));
        assertEquals(BritishCutoverDate.of(1752, 9, 25), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 258));
        assertEquals(BritishCutoverDate.of(1752, 12, 31), BritishCutoverChronology.INSTANCE.dateYearDay(1752, 355)); // last day of 1752
        assertEquals(BritishCutoverDate.of(2014, 1, 1), BritishCutoverChronology.INSTANCE.dateYearDay(2014, 1));
    }

    @Test
    public void test_prolepticYear_specific() {
        // AD era: proleptic year equals year-of-era directly
        assertEquals(4, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.AD, 4));
        assertEquals(3, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.AD, 3));
        assertEquals(2, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.AD, 2));
        assertEquals(1, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.AD, 1));
        // BC era: proleptic year is 1 - year-of-era (BC 1 → 0, BC 2 → -1, ...)
        assertEquals(0, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.BC, 1));
        assertEquals(-1, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.BC, 2));
        assertEquals(-2, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.BC, 3));
        assertEquals(-3, BritishCutoverChronology.INSTANCE.prolepticYear(JulianEra.BC, 4));
    }

    @Test
    public void test_prolepticYear_badEra() {
        // Only JulianEra is accepted; passing an IsoEra must throw ClassCastException
        assertThrows(ClassCastException.class, () -> BritishCutoverChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(JulianEra.AD, BritishCutoverChronology.INSTANCE.eraOf(1));
        assertEquals(JulianEra.BC, BritishCutoverChronology.INSTANCE.eraOf(0));
    }

    @Test
    public void test_Chronology_eraOf_invalid() {
        // Only era values 0 (BC) and 1 (AD) are valid
        assertThrows(DateTimeException.class, () -> BritishCutoverChronology.INSTANCE.eraOf(2));
    }

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = BritishCutoverChronology.INSTANCE.eras();
        assertEquals(2, eras.size());
        assertTrue(eras.contains(JulianEra.BC));
        assertTrue(eras.contains(JulianEra.AD));
    }

    //-----------------------------------------------------------------------
    // Chronology.range
    //-----------------------------------------------------------------------
    @Test
    public void test_Chronology_range() {
        // These ranges reflect the widest possible values across all years.
        // DAY_OF_YEAR minimum is 355 (cutover year 1752) and maximum is 366 (leap year).
        // ALIGNED_WEEK_OF_MONTH minimum is 3 (cutover September 1752 has only 19 days = 3 weeks).
        assertEquals(ValueRange.of(1, 7), BritishCutoverChronology.INSTANCE.range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(1, 28, 31), BritishCutoverChronology.INSTANCE.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 355, 366), BritishCutoverChronology.INSTANCE.range(DAY_OF_YEAR));
        assertEquals(ValueRange.of(1, 12), BritishCutoverChronology.INSTANCE.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(1, 3, 5), BritishCutoverChronology.INSTANCE.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(1, 51, 53), BritishCutoverChronology.INSTANCE.range(ALIGNED_WEEK_OF_YEAR));
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.range
    //-----------------------------------------------------------------------
    /**
     * Expected field ranges for specific dates and fields.
     *
     * <p>Column layout: [year, month, dayOfMonth, temporalField, expectedMin, expectedMax]
     *
     * <p>Ranges are date-specific: e.g. DAY_OF_MONTH max depends on the month and year,
     * and September 1752 has a shortened ALIGNED_WEEK_OF_MONTH range of 1–3 (not 1–5)
     * because the month has only 19 days.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH ranges for each month of 1700 (Julian leap year, Feb=29)
            {1700, 1, 23, DAY_OF_MONTH, 1, 31},
            {1700, 2, 23, DAY_OF_MONTH, 1, 29},
            {1700, 3, 23, DAY_OF_MONTH, 1, 31},
            {1700, 4, 23, DAY_OF_MONTH, 1, 30},
            {1700, 5, 23, DAY_OF_MONTH, 1, 31},
            {1700, 6, 23, DAY_OF_MONTH, 1, 30},
            {1700, 7, 23, DAY_OF_MONTH, 1, 31},
            {1700, 8, 23, DAY_OF_MONTH, 1, 31},
            {1700, 9, 23, DAY_OF_MONTH, 1, 30},
            {1700, 10, 23, DAY_OF_MONTH, 1, 31},
            {1700, 11, 23, DAY_OF_MONTH, 1, 30},
            {1700, 12, 23, DAY_OF_MONTH, 1, 31},

            // DAY_OF_MONTH ranges for each month of 1751 (non-leap, Feb=28)
            {1751, 1, 23, DAY_OF_MONTH, 1, 31},
            {1751, 2, 23, DAY_OF_MONTH, 1, 28},
            {1751, 3, 23, DAY_OF_MONTH, 1, 31},
            {1751, 4, 23, DAY_OF_MONTH, 1, 30},
            {1751, 5, 23, DAY_OF_MONTH, 1, 31},
            {1751, 6, 23, DAY_OF_MONTH, 1, 30},
            {1751, 7, 23, DAY_OF_MONTH, 1, 31},
            {1751, 8, 23, DAY_OF_MONTH, 1, 31},
            {1751, 9, 23, DAY_OF_MONTH, 1, 30},
            {1751, 10, 23, DAY_OF_MONTH, 1, 31},
            {1751, 11, 23, DAY_OF_MONTH, 1, 30},
            {1751, 12, 23, DAY_OF_MONTH, 1, 31},

            // DAY_OF_MONTH ranges for each month of 1752 (Julian leap year, Sep is shortened)
            // Note: September 1752 still has a DAY_OF_MONTH range of 1–30 even though only
            // 19 days are physically present; the "length" is separate from the day range.
            {1752, 1, 23, DAY_OF_MONTH, 1, 31},
            {1752, 2, 23, DAY_OF_MONTH, 1, 29},
            {1752, 3, 23, DAY_OF_MONTH, 1, 31},
            {1752, 4, 23, DAY_OF_MONTH, 1, 30},
            {1752, 5, 23, DAY_OF_MONTH, 1, 31},
            {1752, 6, 23, DAY_OF_MONTH, 1, 30},
            {1752, 7, 23, DAY_OF_MONTH, 1, 31},
            {1752, 8, 23, DAY_OF_MONTH, 1, 31},
            {1752, 9, 23, DAY_OF_MONTH, 1, 30},
            {1752, 10, 23, DAY_OF_MONTH, 1, 31},
            {1752, 11, 23, DAY_OF_MONTH, 1, 30},
            {1752, 12, 23, DAY_OF_MONTH, 1, 31},

            // DAY_OF_MONTH ranges for months in 2012 (Gregorian leap year)
            {2012, 1, 23, DAY_OF_MONTH, 1, 31},
            {2012, 2, 23, DAY_OF_MONTH, 1, 29},
            {2012, 3, 23, DAY_OF_MONTH, 1, 31},
            {2012, 4, 23, DAY_OF_MONTH, 1, 30},
            {2012, 5, 23, DAY_OF_MONTH, 1, 31},
            {2012, 6, 23, DAY_OF_MONTH, 1, 30},
            {2012, 7, 23, DAY_OF_MONTH, 1, 31},
            {2012, 8, 23, DAY_OF_MONTH, 1, 31},
            {2012, 9, 23, DAY_OF_MONTH, 1, 30},
            {2012, 10, 23, DAY_OF_MONTH, 1, 31},
            {2012, 11, 23, DAY_OF_MONTH, 1, 30},
            {2012, 12, 23, DAY_OF_MONTH, 1, 31},
            {2011, 2, 23, DAY_OF_MONTH, 1, 28},  // non-leap year

            // DAY_OF_YEAR ranges — varies by year length
            {1700, 1, 23, DAY_OF_YEAR, 1, 366},  // Julian leap year
            {1751, 1, 23, DAY_OF_YEAR, 1, 365},
            {1752, 1, 23, DAY_OF_YEAR, 1, 355},  // Cutover year: 11 days removed
            {1753, 1, 23, DAY_OF_YEAR, 1, 365},
            {2012, 1, 23, DAY_OF_YEAR, 1, 366},  // Gregorian leap year
            {2011, 2, 23, DAY_OF_YEAR, 1, 365},

            // ALIGNED_WEEK_OF_MONTH ranges for each month of 1752
            // September 1752 has only 19 days (3 complete weeks), so max is 3 rather than 5
            {1752, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 4, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 5, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 6, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 7, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 8, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 9, 23, ALIGNED_WEEK_OF_MONTH, 1, 3},  // shortened month: only 3 aligned weeks
            {1752, 10, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 11, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {1752, 12, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {2012, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {2012, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5},
            {2011, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 4},  // 28 days = exactly 4 aligned weeks

            // ALIGNED_WEEK_OF_YEAR ranges
            {1752, 12, 23, ALIGNED_WEEK_OF_YEAR, 1, 51},  // 355 days / 7 = 50 full weeks + 5 days = 51 partial weeks
            {2011, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53},
            {2012, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53},
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), BritishCutoverDate.of(year, month, dom).range(field));
    }

    @Test
    public void test_range_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> BritishCutoverDate.of(2012, 6, 30).range(MINUTE_OF_DAY));
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.getLong
    //-----------------------------------------------------------------------
    /**
     * Expected long values for specific (year, month, day, field) combinations.
     *
     * <p>Column layout: [year, month, dayOfMonth, temporalField, expectedLongValue]
     *
     * <p>Includes dates in the Julian part of 1752 (before the gap), the cutover date itself,
     * and post-cutover modern dates. The DAY_OF_YEAR arithmetic expressions show the
     * cumulative month lengths to make the expected value self-documenting.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // 1752-05-26: a Julian date well before the cutover gap
            {1752, 5, 26, DAY_OF_WEEK, 2},
            {1752, 5, 26, DAY_OF_MONTH, 26},
            {1752, 5, 26, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 26},  // Jan(31)+Feb(29)+Mar(31)+Apr(30)+26 days in May = 147
            {1752, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5},
            {1752, 5, 26, ALIGNED_WEEK_OF_MONTH, 4},
            {1752, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7},
            {1752, 5, 26, ALIGNED_WEEK_OF_YEAR, 21},
            {1752, 5, 26, MONTH_OF_YEAR, 5},

            // 1752-09-02: the last Julian date before the 11-day cutover gap
            {1752, 9, 2, DAY_OF_WEEK, 3},
            {1752, 9, 2, DAY_OF_MONTH, 2},
            {1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 2},  // Jan–Aug + 2 days in Sep = 246
            {1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2},
            {1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 1},
            {1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1},
            {1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 36},
            {1752, 9, 2, MONTH_OF_YEAR, 9},

            // 1752-09-14: the first Gregorian date (day 247 of the 355-day cutover year)
            // DAY_OF_MONTH is 14 but DAY_OF_YEAR jumps directly from 246 to 247 (11 days removed)
            {1752, 9, 14, DAY_OF_WEEK, 4},
            {1752, 9, 14, DAY_OF_MONTH, 14},
            {1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 3},  // Jan–Aug + 3 ordinal days in Sep = 247
            {1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3},
            {1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 1},
            {1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2},
            {1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 36},
            {1752, 9, 14, MONTH_OF_YEAR, 9},

            // 2014-05-26: a modern post-cutover date; calendar behaviour is pure Gregorian
            {2014, 5, 26, DAY_OF_WEEK, 1},
            {2014, 5, 26, DAY_OF_MONTH, 26},
            {2014, 5, 26, DAY_OF_YEAR, 31 + 28 + 31 + 30 + 26},  // Jan(31)+Feb(28)+Mar(31)+Apr(30)+26 days in May = 146
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21},
            {2014, 5, 26, MONTH_OF_YEAR, 5},
            {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1},
            {2014, 5, 26, YEAR, 2014},
            {2014, 5, 26, ERA, 1},
            // Era field: 1 = AD, 0 = BC
            {1, 6, 8, ERA, 1},
            {0, 6, 8, ERA, 0},

            // ISO week-based day of week (Monday=1 per ISO 8601)
            {2014, 5, 26, WeekFields.ISO.dayOfWeek(), 1},
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, BritishCutoverDate.of(year, month, dom).getLong(field));
    }

    @Test
    public void test_getLong_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> BritishCutoverDate.of(2012, 6, 30).getLong(MINUTE_OF_DAY));
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.with
    //-----------------------------------------------------------------------
    /**
     * Expected results of adjusting a date using a temporal field and value.
     *
     * <p>Column layout: [year, month, dom, field, newValue, expectedYear, expectedMonth, expectedDom]
     *
     * <p>Several rows are marked "lenient": the new day falls in the 11-day cutover gap
     * (3–13 Sep 1752) and is treated as a Julian date, shifting forward by 11 days to
     * produce the nearest valid Gregorian date.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // Adjustments starting from 1752-09-02 (last Julian date before the gap)
            {1752, 9, 2, DAY_OF_WEEK, 1, 1752, 8, 31},
            {1752, 9, 2, DAY_OF_WEEK, 4, 1752, 9, 14},  // Thursday = the first Gregorian day
            {1752, 9, 2, DAY_OF_MONTH, 1, 1752, 9, 1},
            {1752, 9, 2, DAY_OF_MONTH, 3, 1752, 9, 14},   // day 3 falls in gap → shifted to Sep 14 (lenient)
            {1752, 9, 2, DAY_OF_MONTH, 13, 1752, 9, 24},  // day 13 falls in gap → shifted to Sep 24 (lenient)
            {1752, 9, 2, DAY_OF_MONTH, 14, 1752, 9, 14},
            {1752, 9, 2, DAY_OF_MONTH, 30, 1752, 9, 30},
            {1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 1, 1752, 9, 1},   // ordinal day 246+1-1 = Sep 1
            {1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 3, 1752, 9, 14},  // ordinal day 247 = Sep 14
            {1752, 9, 2, DAY_OF_YEAR, 356, 1753, 1, 1},   // beyond 1752's 355 days → lenient overflow to 1753
            {1752, 9, 2, DAY_OF_YEAR, 366, 1753, 1, 11},  // lenient
            {1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 1752, 9, 1},
            {1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 1752, 9, 14},  // 3rd aligned day = Sep 14 (after gap)
            {1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 2, 1752, 9, 20},
            {1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 3, 1752, 9, 27},
            {1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 4, 1752, 10, 4},   // beyond Sep's 3 aligned weeks → lenient
            {1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 5, 1752, 10, 11},  // lenient
            {1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 1752, 9, 14},
            {1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 1752, 9, 15},
            {1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 1, 1752, 1, 1},
            {1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 35, 1752, 8, 26},
            {1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 37, 1752, 9, 20},
            {1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 51, 1752, 12, 27},
            {1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 52, 1753, 1, 3},  // beyond 1752's 51 aligned weeks → lenient
            {1752, 9, 2, MONTH_OF_YEAR, 8, 1752, 8, 2},
            {1752, 9, 2, MONTH_OF_YEAR, 10, 1752, 10, 2},

            // Adjustments starting from 1752-09-14 (first Gregorian date after the gap)
            {1752, 9, 14, DAY_OF_WEEK, 1, 1752, 8, 31},
            {1752, 9, 14, DAY_OF_WEEK, 3, 1752, 9, 2},
            {1752, 9, 14, DAY_OF_MONTH, 1, 1752, 9, 1},
            {1752, 9, 14, DAY_OF_MONTH, 2, 1752, 9, 2},
            {1752, 9, 14, DAY_OF_MONTH, 3, 1752, 9, 14},   // day 3 falls in gap → shifted to Sep 14 (lenient)
            {1752, 9, 14, DAY_OF_MONTH, 30, 1752, 9, 30},
            {1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 1, 1752, 9, 1},
            {1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 2, 1752, 9, 2},
            {1752, 9, 14, DAY_OF_YEAR, 356, 1753, 1, 1},   // lenient
            {1752, 9, 14, DAY_OF_YEAR, 366, 1753, 1, 11},  // lenient
            {1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 1752, 9, 1},
            {1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 1752, 9, 2},
            {1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 2, 1752, 9, 21},
            {1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 3, 1752, 9, 28},
            {1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 4, 1752, 10, 5},   // lenient
            {1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 5, 1752, 10, 12},  // lenient
            {1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 1752, 9, 14},
            {1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 1752, 9, 15},
            {1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 1, 1752, 1, 2},
            {1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 35, 1752, 8, 27},
            {1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 37, 1752, 9, 21},
            {1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 51, 1752, 12, 28},
            {1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 52, 1753, 1, 4},  // lenient
            {1752, 9, 14, MONTH_OF_YEAR, 8, 1752, 8, 14},
            {1752, 9, 14, MONTH_OF_YEAR, 10, 1752, 10, 14},

            // Adjusting into the cutover gap: day of month or year lands on a non-existent date
            {1752, 8, 4, MONTH_OF_YEAR, 9, 1752, 9, 15},   // day 4 in Sep falls in gap → shifted (lenient)
            {1752, 10, 8, MONTH_OF_YEAR, 9, 1752, 9, 19},  // day 8 in Sep falls in gap → shifted (lenient)
            {1751, 9, 4, YEAR, 1752, 1752, 9, 15},          // Sep 4 in 1752 is in the gap → shifted (lenient)
            {1753, 9, 8, YEAR, 1752, 1752, 9, 19},          // Sep 8 in 1752 is in the gap → shifted (lenient)
            {1751, 9, 4, YEAR_OF_ERA, 1752, 1752, 9, 15},   // lenient
            {1753, 9, 8, YEAR_OF_ERA, 1752, 1752, 9, 19},   // lenient

            // Modern post-cutover date adjustments (pure Gregorian)
            {2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 28},
            {2014, 5, 26, DAY_OF_WEEK, 7, 2014, 6, 1},
            {2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31},
            {2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26},
            {2014, 5, 26, DAY_OF_YEAR, 365, 2014, 12, 31},
            {2014, 5, 26, DAY_OF_YEAR, 146, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5},
            {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 22},
            {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2014, 5, 26},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 9},
            {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21, 2014, 5, 26},
            {2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26},
            {2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26},
            {2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26},
            {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26},
            {2014, 5, 26, YEAR, 2012, 2012, 5, 26},
            {2014, 5, 26, YEAR, 2014, 2014, 5, 26},
            {2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26},
            {2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26},
            {2014, 5, 26, ERA, 0, -2013, 5, 26},  // switching from AD to BC: year 2014 AD → -2013 (proleptic)
            {2014, 5, 26, ERA, 1, 2014, 5, 26},

            // Month adjustments that clamp the day to the new month's last valid day
            {2011, 3, 31, MONTH_OF_YEAR, 2, 2011, 2, 28},  // Mar 31 → Feb 28 (non-leap year)
            {2012, 3, 31, MONTH_OF_YEAR, 2, 2012, 2, 29},  // Mar 31 → Feb 29 (Gregorian leap year)
            {2012, 3, 31, MONTH_OF_YEAR, 6, 2012, 6, 30},  // Mar 31 → Jun 30 (June has 30 days)
            {2012, 2, 29, YEAR, 2011, 2011, 2, 28},         // leap day → non-leap year clamps to Feb 28
            {-2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8},

            // ISO day-of-week field (Monday=1)
            {2014, 5, 26, WeekFields.ISO.dayOfWeek(), 2, 2014, 5, 27},
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom), BritishCutoverDate.of(year, month, dom).with(field, value));
    }

    @Test
    public void test_with_TemporalField_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> BritishCutoverDate.of(2012, 6, 30).with(MINUTE_OF_DAY, 0));
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.with(TemporalAdjuster)
    //-----------------------------------------------------------------------
    /**
     * Input date and expected result after applying {@link TemporalAdjusters#lastDayOfMonth()}.
     *
     * <p>Column layout: [inputDate, expectedLastDayOfMonth]
     *
     * <p>September 1752 ends on the 30th even though the month has only 19 days physically;
     * DAY_OF_MONTH range goes up to 30 regardless of the cutover gap.
     */
    public static Object[][] data_lastDayOfMonth() {
        return new Object[][] {
            {BritishCutoverDate.of(1752, 2, 23), BritishCutoverDate.of(1752, 2, 29)},  // Julian leap Feb
            {BritishCutoverDate.of(1752, 6, 23), BritishCutoverDate.of(1752, 6, 30)},
            {BritishCutoverDate.of(1752, 9, 2), BritishCutoverDate.of(1752, 9, 30)},   // last Julian date → last day is still Sep 30
            {BritishCutoverDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 30)},  // first Gregorian date → same result
            {BritishCutoverDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 29)},  // Gregorian leap Feb
            {BritishCutoverDate.of(2012, 6, 23), BritishCutoverDate.of(2012, 6, 30)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_lastDayOfMonth")
    public void test_adjust_lastDayOfMonth(BritishCutoverDate input, BritishCutoverDate expected) {
        BritishCutoverDate test = input.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(expected, test);
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.with(Local*)
    //-----------------------------------------------------------------------
    /**
     * Adjusting a BritishCutoverDate to a given ISO LocalDate.
     *
     * <p>Column layout: [inputDate, targetLocalDate, expectedBritishCutoverDate]
     *
     * <p>The ISO LocalDate is converted to the BritishCutoverDate that represents
     * the same instant. If the ISO date lands in the cutover gap, the result is
     * the first valid date after the gap (Sep 14, 1752).
     */
    public static Object[][] data_withLocalDate() {
        return new Object[][] {
            // ISO 1752-09-12 corresponds to British Julian 1752-09-01 (11-day offset)
            {BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9, 1)},
            {BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9, 1)},
            // ISO 1752-09-14 is the cutover day — same in both calendars
            {BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14)},
            {BritishCutoverDate.of(1752, 9, 15), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14)},
            // Modern date — ISO and British Cutover are identical
            {BritishCutoverDate.of(2012, 2, 23), LocalDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 23)},
        };
    }

    @ParameterizedTest
    @MethodSource("data_withLocalDate")
    public void test_adjust_LocalDate(BritishCutoverDate input, LocalDate local, BritishCutoverDate expected) {
        BritishCutoverDate test = input.with(local);
        assertEquals(expected, test);
    }

    @Test
    public void test_adjust_toMonth() {
        // BritishCutoverDate does not support adjustment via java.time.Month
        BritishCutoverDate cutover = BritishCutoverDate.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> cutover.with(Month.APRIL));
    }

    //-----------------------------------------------------------------------
    // LocalDate.with(BritishCutoverDate)
    //-----------------------------------------------------------------------
    @Test
    public void test_LocalDate_withBritishCutoverDate() {
        BritishCutoverDate cutover = BritishCutoverDate.of(2012, 6, 23);
        LocalDate test = LocalDate.MIN.with(cutover);
        assertEquals(LocalDate.of(2012, 6, 23), test);
    }

    @Test
    public void test_LocalDateTime_withBritishCutoverDate() {
        BritishCutoverDate cutover = BritishCutoverDate.of(2012, 6, 23);
        LocalDateTime test = LocalDateTime.MIN.with(cutover);
        assertEquals(LocalDateTime.of(2012, 6, 23, 0, 0), test);
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.plus
    //-----------------------------------------------------------------------
    /**
     * Addition test cases: start date, amount, unit, and expected result.
     *
     * <p>Column layout: [year, month, dom, amount, unit, expectedYear, expectedMonth, expectedDom, bidi]
     *
     * <p>The {@code bidi} (bidirectional) flag indicates whether the same data row can also
     * verify subtraction: when {@code true}, subtracting {@code amount} from the expected date
     * should yield the original date (used by {@link #test_minus_TemporalUnit}).
     * Rows with {@code bidi=false} represent cases where adding lands in the cutover gap,
     * so the result is shifted — and subtracting from that shifted result would not
     * reverse to the exact original date.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // Adding DAYS around the cutover gap (Sep 2 → Sep 14 is one calendar day)
            {1752, 9, 2, -1, DAYS, 1752, 9, 1, true},
            {1752, 9, 2, 0, DAYS, 1752, 9, 2, true},
            {1752, 9, 2, 1, DAYS, 1752, 9, 14, true},   // +1 day skips the 11-day gap
            {1752, 9, 2, 2, DAYS, 1752, 9, 15, true},
            {1752, 9, 14, -1, DAYS, 1752, 9, 2, true},  // -1 day crosses back over the gap
            {1752, 9, 14, 0, DAYS, 1752, 9, 14, true},
            {1752, 9, 14, 1, DAYS, 1752, 9, 15, true},
            {2014, 5, 26, 0, DAYS, 2014, 5, 26, true},
            {2014, 5, 26, 8, DAYS, 2014, 6, 3, true},
            {2014, 5, 26, -3, DAYS, 2014, 5, 23, true},

            // Adding WEEKS (7 days × n, crossing or straddling the cutover gap)
            {1752, 9, 2, -1, WEEKS, 1752, 8, 26, true},
            {1752, 9, 2, 0, WEEKS, 1752, 9, 2, true},
            {1752, 9, 2, 1, WEEKS, 1752, 9, 20, true},   // +7 days from Sep 2 = Sep 20 (gap consumes 11 days)
            {1752, 9, 14, -1, WEEKS, 1752, 8, 27, true},
            {1752, 9, 14, 0, WEEKS, 1752, 9, 14, true},
            {1752, 9, 14, 1, WEEKS, 1752, 9, 21, true},
            {2014, 5, 26, 0, WEEKS, 2014, 5, 26, true},
            {2014, 5, 26, 3, WEEKS, 2014, 6, 16, true},
            {2014, 5, 26, -5, WEEKS, 2014, 4, 21, true},

            // Adding MONTHS (calendar months; landing in the gap shifts result forward)
            {1752, 9, 2, -1, MONTHS, 1752, 8, 2, true},
            {1752, 9, 2, 0, MONTHS, 1752, 9, 2, true},
            {1752, 9, 2, 1, MONTHS, 1752, 10, 2, true},
            {1752, 9, 14, -1, MONTHS, 1752, 8, 14, true},
            {1752, 9, 14, 0, MONTHS, 1752, 9, 14, true},
            {1752, 9, 14, 1, MONTHS, 1752, 10, 14, true},
            {1752, 8, 12, 1, MONTHS, 1752, 9, 23, false},   // Aug 12 +1 month = Sep 12 → gap → Sep 23 (not reversible)
            {1752, 10, 12, -1, MONTHS, 1752, 9, 23, false},  // Oct 12 -1 month = Sep 12 → gap → Sep 23 (not reversible)
            {2014, 5, 26, 0, MONTHS, 2014, 5, 26, true},
            {2014, 5, 26, 3, MONTHS, 2014, 8, 26, true},
            {2014, 5, 26, -5, MONTHS, 2013, 12, 26, true},

            // Larger time units (years, decades, centuries, millennia, eras)
            {2014, 5, 26, 0, YEARS, 2014, 5, 26, true},
            {2014, 5, 26, 3, YEARS, 2017, 5, 26, true},
            {2014, 5, 26, -5, YEARS, 2009, 5, 26, true},
            {2014, 5, 26, 0, DECADES, 2014, 5, 26, true},
            {2014, 5, 26, 3, DECADES, 2044, 5, 26, true},
            {2014, 5, 26, -5, DECADES, 1964, 5, 26, true},
            {2014, 5, 26, 0, CENTURIES, 2014, 5, 26, true},
            {2014, 5, 26, 3, CENTURIES, 2314, 5, 26, true},
            {2014, 5, 26, -5, CENTURIES, 1514, 5, 26, true},
            {2014, 5, 26, 0, MILLENNIA, 2014, 5, 26, true},
            {2014, 5, 26, 3, MILLENNIA, 5014, 5, 26, true},
            {2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26, true},
            {2014, 5, 26, -1, ERAS, -2013, 5, 26, true},  // switching from AD to BC era
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom, boolean bidi) {
        assertEquals(BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom), BritishCutoverDate.of(year, month, dom).plus(amount, unit));
    }

    /**
     * Verifies subtraction by reusing the {@code data_plus} data set in reverse.
     *
     * <p>The parameter order from {@code data_plus} is reinterpreted here:
     * the "expected" position (first three columns) becomes the subtraction start,
     * and the "input" position (columns 6–8) becomes the expected result. The
     * {@code bidi} flag guards rows where the addition was non-reversible (e.g.
     * because the result was shifted out of the cutover gap).
     */
    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit,
            int year, int month, int dom, boolean bidi) {
        if (bidi) {
            assertEquals(BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom), BritishCutoverDate.of(year, month, dom).minus(amount, unit));
        }
    }

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> BritishCutoverDate.of(2012, 6, 30).plus(0, MINUTES));
    }

    //-----------------------------------------------------------------------
    // BritishCutoverDate.until
    //-----------------------------------------------------------------------
    /**
     * Expected number of complete units between two British Cutover dates.
     *
     * <p>Column layout: [year1, month1, dom1, year2, month2, dom2, unit, expectedCount]
     *
     * <p>The gap between Sep 2 and Sep 14, 1752 counts as 1 calendar day (not 12),
     * and between Sep 14 and Sep 2 is -1 calendar day. This is reflected in the
     * DAYS rows that cross the cutover boundary.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // DAYS across the cutover gap: Sep 2 → Sep 14 = 1 day (the gap collapses to zero elapsed days)
            {1752, 9, 1, 1752, 9, 2, DAYS, 1},
            {1752, 9, 1, 1752, 9, 14, DAYS, 2},   // Sep 1 to Sep 14 = 2 calendar days (Sep 1→2, Sep 2→14)
            {1752, 9, 2, 1752, 9, 14, DAYS, 1},   // Sep 2 to Sep 14 = 1 calendar day
            {1752, 9, 2, 1752, 9, 15, DAYS, 2},
            {1752, 9, 14, 1752, 9, 1, DAYS, -2},
            {1752, 9, 14, 1752, 9, 2, DAYS, -1},
            {2014, 5, 26, 2014, 5, 26, DAYS, 0},
            {2014, 5, 26, 2014, 6, 1, DAYS, 6},
            {2014, 5, 26, 2014, 5, 20, DAYS, -6},

            // WEEKS
            {1752, 9, 1, 1752, 9, 14, WEEKS, 0},   // 2 days < 7 → 0 complete weeks
            {1752, 9, 1, 1752, 9, 18, WEEKS, 0},
            {1752, 9, 1, 1752, 9, 19, WEEKS, 1},
            {1752, 9, 2, 1752, 9, 14, WEEKS, 0},
            {1752, 9, 2, 1752, 9, 19, WEEKS, 0},
            {1752, 9, 2, 1752, 9, 20, WEEKS, 1},
            {2014, 5, 26, 2014, 5, 26, WEEKS, 0},
            {2014, 5, 26, 2014, 6, 1, WEEKS, 0},
            {2014, 5, 26, 2014, 6, 2, WEEKS, 1},

            // MONTHS
            {1752, 9, 1, 1752, 9, 14, MONTHS, 0},
            {1752, 9, 1, 1752, 9, 30, MONTHS, 0},
            {1752, 9, 1, 1752, 10, 1, MONTHS, 1},
            {1752, 9, 2, 1752, 9, 14, MONTHS, 0},
            {1752, 9, 2, 1752, 10, 1, MONTHS, 0},
            {1752, 9, 2, 1752, 10, 2, MONTHS, 1},
            {1752, 9, 14, 1752, 9, 15, MONTHS, 0},
            {1752, 9, 14, 1752, 10, 13, MONTHS, 0},
            {1752, 9, 14, 1752, 10, 14, MONTHS, 1},
            {2014, 5, 26, 2014, 5, 26, MONTHS, 0},
            {2014, 5, 26, 2014, 6, 25, MONTHS, 0},
            {2014, 5, 26, 2014, 6, 26, MONTHS, 1},

            // Larger units
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
            {-2013, 5, 26, 0, 5, 26, ERAS, 0},    // still in BC era (year 0 = 1 BC)
            {-2013, 5, 26, 2014, 5, 26, ERAS, 1}, // crosses from BC to AD
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        BritishCutoverDate start = BritishCutoverDate.of(year1, month1, dom1);
        BritishCutoverDate end = BritishCutoverDate.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }

    @Test
    public void test_until_TemporalUnit_unsupported() {
        BritishCutoverDate start = BritishCutoverDate.of(2012, 6, 30);
        BritishCutoverDate end = BritishCutoverDate.of(2012, 7, 1);
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_plus_Period() {
        // Adding 0y 1m 3d to 1752-09-02: +1 month = Oct 2, then +3 days = Oct 5
        assertEquals(
            BritishCutoverDate.of(1752, 10, 5),
            BritishCutoverDate.of(1752, 9, 2).plus(BritishCutoverChronology.INSTANCE.period(0, 1, 3)));
        // Adding 0y 1m 0d to 1752-08-12: +1 month = Sep 12 (falls in gap) → shifted to Sep 23
        assertEquals(
            BritishCutoverDate.of(1752, 9, 23),
            BritishCutoverDate.of(1752, 8, 12).plus(BritishCutoverChronology.INSTANCE.period(0, 1, 0)));
        // Modern date: 2014-05-26 + 0y 2m 3d = 2014-07-29
        assertEquals(
            BritishCutoverDate.of(2014, 7, 29),
            BritishCutoverDate.of(2014, 5, 26).plus(BritishCutoverChronology.INSTANCE.period(0, 2, 3)));
    }

    @Test
    public void test_plus_Period_ISO() {
        // Adding an ISO Period to a BritishCutoverDate must throw; use BritishCutoverChronology.period() instead
        assertThrows(DateTimeException.class, () -> BritishCutoverDate.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }

    @Test
    public void test_minus_Period() {
        // Subtracting 0y 1m 0d from 1752-10-12: -1 month = Sep 12 (falls in gap) → shifted to Sep 23
        assertEquals(
            BritishCutoverDate.of(1752, 9, 23),
            BritishCutoverDate.of(1752, 10, 12).minus(BritishCutoverChronology.INSTANCE.period(0, 1, 0)));
        // Modern date: 2014-05-26 - 0y 2m 3d = 2014-03-23
        assertEquals(
            BritishCutoverDate.of(2014, 3, 23),
            BritishCutoverDate.of(2014, 5, 26).minus(BritishCutoverChronology.INSTANCE.period(0, 2, 3)));
    }

    @Test
    public void test_minus_Period_ISO() {
        // Subtracting an ISO Period must throw; use BritishCutoverChronology.period() instead
        assertThrows(DateTimeException.class, () -> BritishCutoverDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }

    //-----------------------------------------------------------------------
    /**
     * Expected ChronoPeriod between two British Cutover dates.
     *
     * <p>Column layout: [year1, month1, dom1, year2, month2, dom2, expectedYears, expectedMonths, expectedDays]
     *
     * <p>Dates around the 1752 cutover boundary are heavily exercised to verify that
     * period calculation handles the 11-day gap correctly. Inline comments show the
     * reasoning for expected values relative to the gap.
     */
    public static Object[][] data_untilCLD() {
        return new Object[][] {
            {1752, 7, 2, 1752, 7, 1, 0, 0, -1},
            {1752, 7, 2, 1752, 7, 2, 0, 0, 0},
            {1752, 7, 2, 1752, 9, 1, 0, 1, 30},   // 30 days after 1752-08-02
            {1752, 7, 2, 1752, 9, 2, 0, 2, 0},    // 2 whole months
            {1752, 7, 2, 1752, 9, 14, 0, 2, 1},   // 1 day after 1752-09-02
            {1752, 7, 2, 1752, 9, 30, 0, 2, 17},  // 17 days after 1752-09-02
            {1752, 7, 2, 1752, 10, 1, 0, 2, 18},  // 18 days after 1752-09-02
            {1752, 7, 2, 1752, 10, 2, 0, 3, 0},   // 3 whole months
            {1752, 7, 2, 1752, 10, 3, 0, 3, 1},
            {1752, 7, 2, 1752, 10, 30, 0, 3, 28},
            {1752, 7, 2, 1752, 11, 1, 0, 3, 30},
            {1752, 7, 2, 1752, 11, 2, 0, 4, 0},

            {1752, 7, 3, 1752, 9, 2, 0, 1, 30},    // 30 days after 1752-08-03
            {1752, 7, 3, 1752, 9, 14, 0, 2, 0},    // 2 months
            {1752, 7, 3, 1752, 9, 15, 0, 2, 1},    // 1 day after 1752-09-03 (which maps to 1752-09-14)
            {1752, 7, 3, 1752, 9, 30, 0, 2, 16},   // 16 days after 1752-09-03 (1752-09-14)
            {1752, 7, 3, 1752, 10, 1, 0, 2, 17},   // 17 days after 1752-09-03 (1752-09-14)
            {1752, 7, 3, 1752, 10, 3, 0, 3, 0},
            {1752, 7, 3, 1752, 10, 4, 0, 3, 1},

            {1752, 7, 4, 1752, 9, 2, 0, 1, 29},    // 29 days after 1752-08-04
            {1752, 7, 4, 1752, 9, 14, 0, 1, 30},   // 30 days after 1752-08-04
            {1752, 7, 4, 1752, 9, 15, 0, 2, 0},    // 2 months
            {1752, 7, 4, 1752, 9, 30, 0, 2, 15},   // 15 days after 1752-09-04 (1752-09-15)
            {1752, 7, 4, 1752, 10, 1, 0, 2, 16},   // 16 days after 1752-09-04 (1752-09-15)
            {1752, 7, 4, 1752, 10, 4, 0, 3, 0},
            {1752, 7, 4, 1752, 10, 5, 0, 3, 1},

            {1752, 7, 13, 1752, 9, 2, 0, 1, 20},   // 20 days after 1752-08-13
            {1752, 7, 13, 1752, 9, 14, 0, 1, 21},  // 21 days after 752-08-13
            {1752, 7, 13, 1752, 9, 24, 0, 2, 0},   // 2 months
            {1752, 7, 13, 1752, 9, 30, 0, 2, 6},   // 6 days after 1752-09-13 (1752-09-24)
            {1752, 7, 13, 1752, 10, 1, 0, 2, 7},   // 7 days after 1752-09-13 (1752-09-24)
            {1752, 7, 13, 1752, 10, 12, 0, 2, 18}, // 18 days after 1752-09-13 (1752-09-24)
            {1752, 7, 13, 1752, 10, 13, 0, 3, 0},
            {1752, 7, 13, 1752, 10, 14, 0, 3, 1},

            {1752, 7, 14, 1752, 9, 2, 0, 1, 19},   // 19 days after 1752-08-14
            {1752, 7, 14, 1752, 9, 14, 0, 2, 0},   // 2 months
            {1752, 7, 14, 1752, 9, 15, 0, 2, 1},   // 1 day after 1752-09-14
            {1752, 7, 14, 1752, 9, 30, 0, 2, 16},  // 16 days after 1752-09-14
            {1752, 7, 14, 1752, 10, 1, 0, 2, 17},  // 17 days after 1752-09-14
            {1752, 7, 14, 1752, 10, 13, 0, 2, 29}, // 29 days after 1752-09-14
            {1752, 7, 14, 1752, 10, 14, 0, 3, 0},
            {1752, 7, 14, 1752, 10, 15, 0, 3, 1},

            {1752, 8, 2, 1752, 9, 2, 0, 1, 0},
            {1752, 8, 2, 1752, 9, 14, 0, 1, 1},    // 1 day after 1752-09-02
            {1752, 8, 2, 1752, 9, 30, 0, 1, 17},   // 17 days after 1752-09-02
            {1752, 8, 2, 1752, 10, 1, 0, 1, 18},   // 18 days after 1752-09-02
            {1752, 8, 2, 1752, 10, 2, 0, 2, 0},
            {1752, 8, 2, 1752, 10, 3, 0, 2, 1},
            {1752, 8, 2, 1752, 10, 30, 0, 2, 28},

            {1752, 8, 16, 1752, 9, 2, 0, 0, 17},
            {1752, 8, 16, 1752, 9, 14, 0, 0, 18},
            {1752, 8, 16, 1752, 9, 15, 0, 0, 19},
            {1752, 8, 16, 1752, 9, 16, 0, 1, 0},
            {1752, 8, 16, 1752, 10, 2, 0, 1, 16},
            {1752, 8, 16, 1752, 10, 15, 0, 1, 29},
            {1752, 8, 16, 1752, 10, 16, 0, 2, 0},
            {1752, 8, 16, 1752, 10, 17, 0, 2, 1},

            {1752, 9, 1, 1752, 8, 31, 0, 0, -1},
            {1752, 9, 1, 1752, 9, 1, 0, 0, 0},
            {1752, 9, 1, 1752, 9, 2, 0, 0, 1},
            {1752, 9, 1, 1752, 9, 14, 0, 0, 2},   // Sep 1 to Sep 14 = 2 days (via Sep 2)
            {1752, 9, 1, 1752, 9, 15, 0, 0, 3},
            {1752, 9, 1, 1752, 9, 30, 0, 0, 18},
            {1752, 9, 1, 1752, 10, 1, 0, 1, 0},
            {1752, 9, 1, 1752, 10, 2, 0, 1, 1},

            {1752, 9, 2, 1752, 8, 31, 0, 0, -2},
            {1752, 9, 2, 1752, 9, 1, 0, 0, -1},
            {1752, 9, 2, 1752, 9, 2, 0, 0, 0},
            {1752, 9, 2, 1752, 9, 14, 0, 0, 1},   // last Julian day to first Gregorian day = 1 calendar day
            {1752, 9, 2, 1752, 9, 30, 0, 0, 17},
            {1752, 9, 2, 1752, 10, 1, 0, 0, 18},
            {1752, 9, 2, 1752, 10, 2, 0, 1, 0},
            {1752, 9, 2, 1752, 10, 3, 0, 1, 1},
            {1752, 9, 2, 1752, 11, 1, 0, 1, 30},
            {1752, 9, 2, 1752, 11, 2, 0, 2, 0},
            {1752, 9, 2, 1752, 11, 3, 0, 2, 1},

            // From 1752-09-14 going backward into the Julian part
            {1752, 9, 14, 1752, 7, 13, 0, -2, -1},
            {1752, 9, 14, 1752, 7, 14, 0, -2, 0},
            {1752, 9, 14, 1752, 8, 13, 0, -1, -1},
            {1752, 9, 14, 1752, 8, 14, 0, -1, 0},
            {1752, 9, 14, 1752, 8, 15, 0, 0, -19},  // 19 days before (Sep 14 - 19 = Aug 26, skipping gap = Aug 15)
            {1752, 9, 14, 1752, 8, 31, 0, 0, -3},   // 3 days before
            {1752, 9, 14, 1752, 9, 1, 0, 0, -2},    // 2 days before
            {1752, 9, 14, 1752, 9, 2, 0, 0, -1},    // 1 day before (last Julian day)
            {1752, 9, 14, 1752, 9, 14, 0, 0, 0},
            {1752, 9, 14, 1752, 9, 15, 0, 0, 1},
            {1752, 9, 14, 1752, 9, 30, 0, 0, 16},
            {1752, 9, 14, 1752, 10, 13, 0, 0, 29},
            {1752, 9, 14, 1752, 10, 14, 0, 1, 0},
            {1752, 9, 14, 1752, 10, 15, 0, 1, 1},

            // From 1752-09-24 (a post-cutover Gregorian date)
            {1752, 9, 24, 1752, 7, 23, 0, -2, -1},
            {1752, 9, 24, 1752, 7, 24, 0, -2, 0},
            {1752, 9, 24, 1752, 8, 23, 0, -1, -1},
            {1752, 9, 24, 1752, 8, 24, 0, -1, 0},
            {1752, 9, 24, 1752, 8, 25, 0, 0, -19},  // 19 days before
            {1752, 9, 24, 1752, 8, 31, 0, 0, -13},  // 13 days before
            {1752, 9, 24, 1752, 9, 1, 0, 0, -12},   // 12 days before
            {1752, 9, 24, 1752, 9, 2, 0, 0, -11},   // 11 days before
            {1752, 9, 24, 1752, 9, 14, 0, 0, -10},  // 10 days before
            {1752, 9, 24, 1752, 9, 23, 0, 0, -1},   // 1 day before
            {1752, 9, 24, 1752, 9, 24, 0, 0, 0},
            {1752, 9, 24, 1752, 9, 25, 0, 0, 1},
            {1752, 9, 24, 1752, 9, 30, 0, 0, 6},
            {1752, 9, 24, 1752, 10, 23, 0, 0, 29},
            {1752, 9, 24, 1752, 10, 24, 0, 1, 0},
            {1752, 9, 24, 1752, 10, 25, 0, 1, 1},

            // Backward from October 3
            {1752, 10, 3, 1752, 10, 1, 0, 0, -2},
            {1752, 10, 3, 1752, 9, 30, 0, 0, -3},
            {1752, 10, 3, 1752, 9, 16, 0, 0, -17},
            {1752, 10, 3, 1752, 9, 15, 0, 0, -18},
            {1752, 10, 3, 1752, 9, 14, 0, 0, -19},
            {1752, 10, 3, 1752, 9, 2, 0, -1, -1},
            {1752, 10, 3, 1752, 9, 1, 0, -1, -2},
            {1752, 10, 3, 1752, 8, 31, 0, -1, -3},
            {1752, 10, 3, 1752, 8, 4, 0, -1, -30},
            {1752, 10, 3, 1752, 8, 3, 0, -2, 0},
            {1752, 10, 3, 1752, 8, 2, 0, -2, -1},

            // Backward from October 4
            {1752, 10, 4, 1752, 10, 1, 0, 0, -3},
            {1752, 10, 4, 1752, 9, 30, 0, 0, -4},
            {1752, 10, 4, 1752, 9, 16, 0, 0, -18},
            {1752, 10, 4, 1752, 9, 15, 0, 0, -19},
            {1752, 10, 4, 1752, 9, 14, 0, 0, -20},
            {1752, 10, 4, 1752, 9, 2, 0, -1, -2},
            {1752, 10, 4, 1752, 9, 1, 0, -1, -3},
            {1752, 10, 4, 1752, 8, 31, 0, -1, -4},
            {1752, 10, 4, 1752, 8, 5, 0, -1, -30},
            {1752, 10, 4, 1752, 8, 4, 0, -2, 0},
            {1752, 10, 4, 1752, 8, 3, 0, -2, -1},
        };
    }

    @ParameterizedTest
    @MethodSource("data_untilCLD")
    public void test_until_CLD(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int expectedYears, int expectedMonths, int expectedDays) {
        BritishCutoverDate a = BritishCutoverDate.of(year1, month1, dom1);
        BritishCutoverDate b = BritishCutoverDate.of(year2, month2, dom2);
        ChronoPeriod c = a.until(b);
        assertEquals(
            BritishCutoverChronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays),
            c);
    }

    @ParameterizedTest
    @MethodSource("data_untilCLD")
    public void test_until_CLD_plus(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int expectedYears, int expectedMonths, int expectedDays) {
        BritishCutoverDate a = BritishCutoverDate.of(year1, month1, dom1);
        BritishCutoverDate b = BritishCutoverDate.of(year2, month2, dom2);
        ChronoPeriod c = a.until(b);
        // Adding the period back to the start date must reproduce the end date
        assertEquals(b, a.plus(c));
    }

    //-------------------------------------------------------------------------
    // atTime(LocalTime)
    //-------------------------------------------------------------------------
    @Test
    public void test_atTime() {
        BritishCutoverDate date = BritishCutoverDate.of(2014, 10, 12);
        ChronoLocalDateTime<BritishCutoverDate> test = date.atTime(LocalTime.of(12, 30));
        assertEquals(date, test.toLocalDate());
        assertEquals(LocalTime.of(12, 30), test.toLocalTime());
        ChronoLocalDateTime<BritishCutoverDate> test2 =
            BritishCutoverChronology.INSTANCE.localDateTime(LocalDateTime.from(test));
        assertEquals(test, test2);
    }

    @Test
    public void test_atTime_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> BritishCutoverDate.of(2014, 5, 26).atTime(null));
    }

    //-----------------------------------------------------------------------
    // check against GregorianCalendar
    //-----------------------------------------------------------------------
    @Test
    public void test_crossCheck() {
        // Verify BritishCutoverDate behaviour matches java.util.GregorianCalendar configured with the
        // same Julian-to-Gregorian cutover date (14 Sep 1752). Iterates every day from 1700 to 1800.
        BritishCutoverDate test = BritishCutoverDate.of(1700, 1, 1);
        BritishCutoverDate end = BritishCutoverDate.of(1800, 1, 1);
        Instant cutover = ZonedDateTime.of(1752, 9, 14, 0, 0, 0, 0, ZoneOffset.UTC).toInstant();
        GregorianCalendar gcal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gcal.setGregorianChange(Date.from(cutover));
        gcal.clear();
        gcal.set(1700, Calendar.JANUARY, 1);
        while (test.isBefore(end)) {
            assertEquals(gcal.get(Calendar.YEAR), test.get(YEAR_OF_ERA));
            assertEquals(gcal.get(Calendar.MONTH) + 1, test.get(MONTH_OF_YEAR));
            assertEquals(gcal.get(Calendar.DAY_OF_MONTH), test.get(DAY_OF_MONTH));
            assertEquals(gcal.toZonedDateTime().toLocalDate(), LocalDate.from(test));
            gcal.add(Calendar.DAY_OF_MONTH, 1);
            test = test.plus(1, DAYS);
        }
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        new EqualsTester()
            .addEqualityGroup(BritishCutoverDate.of(2000, 1, 3), BritishCutoverDate.of(2000, 1, 3))
            .addEqualityGroup(BritishCutoverDate.of(2000, 1, 4), BritishCutoverDate.of(2000, 1, 4))
            .addEqualityGroup(BritishCutoverDate.of(2000, 2, 3), BritishCutoverDate.of(2000, 2, 3))
            .addEqualityGroup(BritishCutoverDate.of(2001, 1, 3), BritishCutoverDate.of(2001, 1, 3))
            .testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    /**
     * Expected string representation of BritishCutoverDate instances.
     *
     * <p>Column layout: [date, expectedToStringOutput]
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            {BritishCutoverDate.of(1, 1, 1), "BritishCutover AD 1-01-01"},
            {BritishCutoverDate.of(2012, 6, 23), "BritishCutover AD 2012-06-23"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(BritishCutoverDate cutover, String expected) {
        assertEquals(expected, cutover.toString());
    }

}
