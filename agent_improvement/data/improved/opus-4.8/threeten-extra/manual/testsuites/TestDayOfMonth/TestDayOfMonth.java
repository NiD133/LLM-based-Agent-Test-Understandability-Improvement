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
package org.threeten.extra;

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_DAY;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.MICRO_OF_DAY;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MILLI_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import com.google.common.testing.EqualsTester;

/**
 * Test DayOfMonth.
 */
public class TestDayOfMonth {

    private static final int MAX_LENGTH = 31;
    private static final DayOfMonth TEST = DayOfMonth.of(12);
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    // Length of each month (index 0 = January) in a non-leap year; February has 28 days.
    private static final int[] MONTH_LENGTHS_NON_LEAP =
            {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static class TestingField implements TemporalField {

        public static final TestingField INSTANCE = new TestingField();

        @Override
        public TemporalUnit getBaseUnit() {
            return ChronoUnit.DAYS;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return ChronoUnit.MONTHS;
        }

        @Override
        public ValueRange range() {
            return ValueRange.of(1, 28, 31);
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            return temporal.isSupported(DAY_OF_MONTH);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return range();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(DAY_OF_MONTH);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            return (R) temporal.with(DAY_OF_MONTH, newValue);
        }
    }

    /**
     * Walks consecutive dates starting at {@code startDate}, covering the given month
     * lengths in order, and asserts that {@code DayOfMonth.from(date)} reports the
     * expected day-of-month (1, 2, 3, ... within each month).
     *
     * @param startDate     the first date to check (must be the 1st of a month)
     * @param monthLengths  the number of days in each successive month to walk through
     */
    private static void assertDayOfMonthFromConsecutiveDates(LocalDate startDate, int[] monthLengths) {
        LocalDate date = startDate;
        for (int lengthOfMonth : monthLengths) {
            for (int expectedDayOfMonth = 1; expectedDayOfMonth <= lengthOfMonth; expectedDayOfMonth++) {
                assertEquals(expectedDayOfMonth, DayOfMonth.from(date).getValue());
                date = date.plusDays(1);
            }
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(Comparable.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfMonth.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfMonth.class));
    }

    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        DayOfMonth test = DayOfMonth.of(1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertEquals(test, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_of_int_singleton() {
        for (int dayOfMonth = 1; dayOfMonth <= MAX_LENGTH; dayOfMonth++) {
            DayOfMonth test = DayOfMonth.of(dayOfMonth);
            assertEquals(dayOfMonth, test.getValue());
            // of() must return the cached singleton for a given value.
            assertSame(test, DayOfMonth.of(dayOfMonth));
        }
    }

    @Test
    public void test_of_int_tooLow() {
        assertThrows(DateTimeException.class, () -> DayOfMonth.of(0));
    }

    @Test
    public void test_of_int_tooHigh() {
        assertThrows(DateTimeException.class, () -> DayOfMonth.of(32));
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_notLeapYear() {
        // 2007 is not a leap year, so use the standard month lengths (February = 28).
        LocalDate startOfYear = LocalDate.of(2007, 1, 1);
        assertDayOfMonthFromConsecutiveDates(startOfYear, MONTH_LENGTHS_NON_LEAP);
    }

    @Test
    public void test_from_TemporalAccessor_leapYear() {
        // 2008 is a leap year; check January (31), February (29) and March (31).
        LocalDate startOfYear = LocalDate.of(2008, 1, 1);
        int[] leapJanToMarLengths = {31, 29, 31};
        assertDayOfMonthFromConsecutiveDates(startOfYear, leapJanToMarLengths);
    }

    @Test
    public void test_from_TemporalAccessor_DayOfMonth() {
        DayOfMonth dom = DayOfMonth.of(6);
        assertEquals(dom, DayOfMonth.from(dom));
    }

    @Test
    public void test_from_TemporalAccessor_nonIso() {
        LocalDate date = LocalDate.now();
        assertEquals(date.getDayOfMonth(), DayOfMonth.from(JapaneseDate.from(date)).getValue());
    }

    @Test
    public void test_from_TemporalAccessor_noDerive() {
        assertThrows(DateTimeException.class, () -> DayOfMonth.from(LocalTime.NOON));
    }

    @Test
    public void test_from_TemporalAccessor_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> DayOfMonth.from((TemporalAccessor) null));
    }

    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        assertEquals(DayOfMonth.of(3), formatter.parse("3", DayOfMonth::from));
    }

    //-----------------------------------------------------------------------
    // isSupported(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        // Only DAY_OF_MONTH (and a custom field backed by it) is supported; everything else is not.
        assertEquals(false, TEST.isSupported((TemporalField) null));
        assertEquals(false, TEST.isSupported(NANO_OF_SECOND));
        assertEquals(false, TEST.isSupported(NANO_OF_DAY));
        assertEquals(false, TEST.isSupported(MICRO_OF_SECOND));
        assertEquals(false, TEST.isSupported(MICRO_OF_DAY));
        assertEquals(false, TEST.isSupported(MILLI_OF_SECOND));
        assertEquals(false, TEST.isSupported(MILLI_OF_DAY));
        assertEquals(false, TEST.isSupported(SECOND_OF_MINUTE));
        assertEquals(false, TEST.isSupported(SECOND_OF_DAY));
        assertEquals(false, TEST.isSupported(MINUTE_OF_HOUR));
        assertEquals(false, TEST.isSupported(MINUTE_OF_DAY));
        assertEquals(false, TEST.isSupported(HOUR_OF_AMPM));
        assertEquals(false, TEST.isSupported(CLOCK_HOUR_OF_AMPM));
        assertEquals(false, TEST.isSupported(HOUR_OF_DAY));
        assertEquals(false, TEST.isSupported(CLOCK_HOUR_OF_DAY));
        assertEquals(false, TEST.isSupported(AMPM_OF_DAY));
        assertEquals(false, TEST.isSupported(DAY_OF_WEEK));
        assertEquals(false, TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(false, TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(true, TEST.isSupported(DAY_OF_MONTH));
        assertEquals(false, TEST.isSupported(DAY_OF_YEAR));
        assertEquals(false, TEST.isSupported(EPOCH_DAY));
        assertEquals(false, TEST.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertEquals(false, TEST.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertEquals(false, TEST.isSupported(MONTH_OF_YEAR));
        assertEquals(false, TEST.isSupported(PROLEPTIC_MONTH));
        assertEquals(false, TEST.isSupported(YEAR_OF_ERA));
        assertEquals(false, TEST.isSupported(YEAR));
        assertEquals(false, TEST.isSupported(ERA));
        assertEquals(false, TEST.isSupported(INSTANT_SECONDS));
        assertEquals(false, TEST.isSupported(OFFSET_SECONDS));
        assertEquals(false, TEST.isSupported(IsoFields.DAY_OF_QUARTER));
        assertEquals(true, TEST.isSupported(TestingField.INSTANCE));
    }

    //-----------------------------------------------------------------------
    // range(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        assertEquals(DAY_OF_MONTH.range(), TEST.range(DAY_OF_MONTH));
    }

    @Test
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.range(MONTH_OF_YEAR));
    }

    @Test
    public void test_range_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.range((TemporalField) null));
    }

    //-----------------------------------------------------------------------
    // get(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_get() {
        assertEquals(12, TEST.get(DAY_OF_MONTH));
    }

    @Test
    public void test_get_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.get(MONTH_OF_YEAR));
    }

    @Test
    public void test_get_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.get((TemporalField) null));
    }

    //-----------------------------------------------------------------------
    // getLong(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_getLong() {
        assertEquals(12L, TEST.getLong(DAY_OF_MONTH));
    }

    @Test
    public void test_getLong_derivedField() {
        assertEquals(12L, TEST.getLong(TestingField.INSTANCE));
    }

    @Test
    public void test_getLong_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.getLong(MONTH_OF_YEAR));
    }

    @Test
    public void test_getLong_invalidField2() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.getLong(IsoFields.DAY_OF_QUARTER));
    }

    @Test
    public void test_getLong_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.getLong((TemporalField) null));
    }

    //-----------------------------------------------------------------------
    // isValidYearMonth(YearMonth)
    //-----------------------------------------------------------------------
    @Test
    public void test_isValidYearMonth_31() {
        // Day 31 only exists in months that have 31 days.
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 1)));   // Jan
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2012, 2)));  // Feb
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 3)));   // Mar
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2012, 4)));  // Apr
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 5)));   // May
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2012, 6)));  // Jun
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 7)));   // Jul
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 8)));   // Aug
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2012, 9)));  // Sep
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 10)));  // Oct
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2012, 11))); // Nov
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 12)));  // Dec
    }

    @Test
    public void test_isValidYearMonth_30() {
        // Day 30 is valid in every month except February.
        DayOfMonth test = DayOfMonth.of(30);
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 1)));   // Jan
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2012, 2)));  // Feb
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 3)));   // Mar
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 4)));   // Apr
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 5)));   // May
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 6)));   // Jun
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 7)));   // Jul
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 8)));   // Aug
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 9)));   // Sep
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 10)));  // Oct
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 11)));  // Nov
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 12)));  // Dec
    }

    @Test
    public void test_isValidYearMonth_29() {
        // Day 29 is valid in every month of a leap year (2012), but not February of a non-leap year (2011).
        DayOfMonth test = DayOfMonth.of(29);
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 1)));   // Jan
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 2)));   // Feb (leap year)
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 3)));   // Mar
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 4)));   // Apr
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 5)));   // May
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 6)));   // Jun
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 7)));   // Jul
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 8)));   // Aug
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 9)));   // Sep
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 10)));  // Oct
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 11)));  // Nov
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 12)));  // Dec
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2011, 2)));  // Feb (non-leap year)
    }

    @Test
    public void test_isValidYearMonth_28() {
        // Day 28 is valid in every month of every year.
        DayOfMonth test = DayOfMonth.of(28);
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 1)));   // Jan
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 2)));   // Feb
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 3)));   // Mar
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 4)));   // Apr
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 5)));   // May
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 6)));   // Jun
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 7)));   // Jul
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 8)));   // Aug
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 9)));   // Sep
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 10)));  // Oct
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 11)));  // Nov
        assertEquals(true, test.isValidYearMonth(YearMonth.of(2012, 12)));  // Dec
    }

    @Test
    public void test_isValidYearMonth_null() {
        assertFalse(TEST.isValidYearMonth((YearMonth) null));
    }

    //-----------------------------------------------------------------------
    // query(TemporalQuery)
    //-----------------------------------------------------------------------
    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, TEST.query(TemporalQueries.chronology()));
        assertEquals(null, TEST.query(TemporalQueries.localDate()));
        assertEquals(null, TEST.query(TemporalQueries.localTime()));
        assertEquals(null, TEST.query(TemporalQueries.offset()));
        assertEquals(ChronoUnit.DAYS, TEST.query(TemporalQueries.precision()));
        assertEquals(null, TEST.query(TemporalQueries.zone()));
        assertEquals(null, TEST.query(TemporalQueries.zoneId()));
    }

    //-----------------------------------------------------------------------
    // adjustInto(Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto() {
        // Adjusting Jan 1st with each day-of-month 1..31 should yield Jan 1st..Jan 31st.
        LocalDate base = LocalDate.of(2007, 1, 1);
        LocalDate expected = base;
        for (int dayOfMonth = 1; dayOfMonth <= MAX_LENGTH; dayOfMonth++) {  // Jan
            Temporal result = DayOfMonth.of(dayOfMonth).adjustInto(base);
            assertEquals(expected, result);
            expected = expected.plusDays(1);
        }
    }

    @Test
    public void test_adjustInto_april31() {
        // April has only 30 days, so day 31 cannot be applied.
        LocalDate base = LocalDate.of(2007, 4, 1);
        DayOfMonth test = DayOfMonth.of(31);
        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }

    @Test
    public void test_adjustInto_february29_notLeapYear() {
        // February 2007 has only 28 days, so day 29 cannot be applied.
        LocalDate base = LocalDate.of(2007, 2, 1);
        DayOfMonth test = DayOfMonth.of(29);
        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }

    @Test
    public void test_adjustInto_nonIso() {
        assertThrows(DateTimeException.class, () -> TEST.adjustInto(JapaneseDate.now()));
    }

    @Test
    public void test_adjustInto_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.adjustInto((Temporal) null));
    }

    //-----------------------------------------------------------------------
    // atMonth(Month)
    //-----------------------------------------------------------------------
    @Test
    public void test_atMonth_Month_31() {
        // Day 31 is clamped down to each month's last valid day.
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(MonthDay.of(1, 31), test.atMonth(JANUARY));
        assertEquals(MonthDay.of(2, 29), test.atMonth(FEBRUARY));
        assertEquals(MonthDay.of(3, 31), test.atMonth(MARCH));
        assertEquals(MonthDay.of(4, 30), test.atMonth(APRIL));
        assertEquals(MonthDay.of(5, 31), test.atMonth(MAY));
        assertEquals(MonthDay.of(6, 30), test.atMonth(JUNE));
        assertEquals(MonthDay.of(7, 31), test.atMonth(JULY));
        assertEquals(MonthDay.of(8, 31), test.atMonth(AUGUST));
        assertEquals(MonthDay.of(9, 30), test.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(10, 31), test.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 30), test.atMonth(NOVEMBER));
        assertEquals(MonthDay.of(12, 31), test.atMonth(DECEMBER));
    }

    @Test
    public void test_atMonth_Month_28() {
        // Day 28 is valid in every month, so no clamping occurs.
        DayOfMonth test = DayOfMonth.of(28);
        assertEquals(MonthDay.of(1, 28), test.atMonth(JANUARY));
        assertEquals(MonthDay.of(2, 28), test.atMonth(FEBRUARY));
        assertEquals(MonthDay.of(3, 28), test.atMonth(MARCH));
        assertEquals(MonthDay.of(4, 28), test.atMonth(APRIL));
        assertEquals(MonthDay.of(5, 28), test.atMonth(MAY));
        assertEquals(MonthDay.of(6, 28), test.atMonth(JUNE));
        assertEquals(MonthDay.of(7, 28), test.atMonth(JULY));
        assertEquals(MonthDay.of(8, 28), test.atMonth(AUGUST));
        assertEquals(MonthDay.of(9, 28), test.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(10, 28), test.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 28), test.atMonth(NOVEMBER));
        assertEquals(MonthDay.of(12, 28), test.atMonth(DECEMBER));
    }

    @Test
    public void test_atMonth_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.atMonth((Month) null));
    }

    //-----------------------------------------------------------------------
    // atMonth(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_atMonth_int_31() {
        // Day 31 is clamped down to each month's last valid day (months given as 1..12).
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(MonthDay.of(1, 31), test.atMonth(1));
        assertEquals(MonthDay.of(2, 29), test.atMonth(2));
        assertEquals(MonthDay.of(3, 31), test.atMonth(3));
        assertEquals(MonthDay.of(4, 30), test.atMonth(4));
        assertEquals(MonthDay.of(5, 31), test.atMonth(5));
        assertEquals(MonthDay.of(6, 30), test.atMonth(6));
        assertEquals(MonthDay.of(7, 31), test.atMonth(7));
        assertEquals(MonthDay.of(8, 31), test.atMonth(8));
        assertEquals(MonthDay.of(9, 30), test.atMonth(9));
        assertEquals(MonthDay.of(10, 31), test.atMonth(10));
        assertEquals(MonthDay.of(11, 30), test.atMonth(11));
        assertEquals(MonthDay.of(12, 31), test.atMonth(12));
    }

    @Test
    public void test_atMonth_int_28() {
        // Day 28 is valid in every month, so no clamping occurs (months given as 1..12).
        DayOfMonth test = DayOfMonth.of(28);
        assertEquals(MonthDay.of(1, 28), test.atMonth(1));
        assertEquals(MonthDay.of(2, 28), test.atMonth(2));
        assertEquals(MonthDay.of(3, 28), test.atMonth(3));
        assertEquals(MonthDay.of(4, 28), test.atMonth(4));
        assertEquals(MonthDay.of(5, 28), test.atMonth(5));
        assertEquals(MonthDay.of(6, 28), test.atMonth(6));
        assertEquals(MonthDay.of(7, 28), test.atMonth(7));
        assertEquals(MonthDay.of(8, 28), test.atMonth(8));
        assertEquals(MonthDay.of(9, 28), test.atMonth(9));
        assertEquals(MonthDay.of(10, 28), test.atMonth(10));
        assertEquals(MonthDay.of(11, 28), test.atMonth(11));
        assertEquals(MonthDay.of(12, 28), test.atMonth(12));
    }

    @Test
    public void test_atMonth_tooLow() {
        assertThrows(DateTimeException.class, () -> TEST.atMonth(0));
    }

    @Test
    public void test_atMonth_tooHigh() {
        assertThrows(DateTimeException.class, () -> TEST.atMonth(13));
    }

    //-----------------------------------------------------------------------
    // atYearMonth(YearMonth)
    //-----------------------------------------------------------------------
    @Test
    public void test_atYearMonth_31() {
        // Day 31 is clamped down to each year-month's last valid day.
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(LocalDate.of(2012, 1, 31), test.atYearMonth(YearMonth.of(2012, 1)));
        assertEquals(LocalDate.of(2012, 2, 29), test.atYearMonth(YearMonth.of(2012, 2)));
        assertEquals(LocalDate.of(2012, 3, 31), test.atYearMonth(YearMonth.of(2012, 3)));
        assertEquals(LocalDate.of(2012, 4, 30), test.atYearMonth(YearMonth.of(2012, 4)));
        assertEquals(LocalDate.of(2012, 5, 31), test.atYearMonth(YearMonth.of(2012, 5)));
        assertEquals(LocalDate.of(2012, 6, 30), test.atYearMonth(YearMonth.of(2012, 6)));
        assertEquals(LocalDate.of(2012, 7, 31), test.atYearMonth(YearMonth.of(2012, 7)));
        assertEquals(LocalDate.of(2012, 8, 31), test.atYearMonth(YearMonth.of(2012, 8)));
        assertEquals(LocalDate.of(2012, 9, 30), test.atYearMonth(YearMonth.of(2012, 9)));
        assertEquals(LocalDate.of(2012, 10, 31), test.atYearMonth(YearMonth.of(2012, 10)));
        assertEquals(LocalDate.of(2012, 11, 30), test.atYearMonth(YearMonth.of(2012, 11)));
        assertEquals(LocalDate.of(2012, 12, 31), test.atYearMonth(YearMonth.of(2012, 12)));
        // Non-leap February clamps day 31 to the 28th.
        assertEquals(LocalDate.of(2011, 2, 28), test.atYearMonth(YearMonth.of(2011, 2)));
    }

    @Test
    public void test_atYearMonth_28() {
        // Day 28 is valid in every year-month, so no clamping occurs.
        DayOfMonth test = DayOfMonth.of(28);
        assertEquals(LocalDate.of(2012, 1, 28), test.atYearMonth(YearMonth.of(2012, 1)));
        assertEquals(LocalDate.of(2012, 2, 28), test.atYearMonth(YearMonth.of(2012, 2)));
        assertEquals(LocalDate.of(2012, 3, 28), test.atYearMonth(YearMonth.of(2012, 3)));
        assertEquals(LocalDate.of(2012, 4, 28), test.atYearMonth(YearMonth.of(2012, 4)));
        assertEquals(LocalDate.of(2012, 5, 28), test.atYearMonth(YearMonth.of(2012, 5)));
        assertEquals(LocalDate.of(2012, 6, 28), test.atYearMonth(YearMonth.of(2012, 6)));
        assertEquals(LocalDate.of(2012, 7, 28), test.atYearMonth(YearMonth.of(2012, 7)));
        assertEquals(LocalDate.of(2012, 8, 28), test.atYearMonth(YearMonth.of(2012, 8)));
        assertEquals(LocalDate.of(2012, 9, 28), test.atYearMonth(YearMonth.of(2012, 9)));
        assertEquals(LocalDate.of(2012, 10, 28), test.atYearMonth(YearMonth.of(2012, 10)));
        assertEquals(LocalDate.of(2012, 11, 28), test.atYearMonth(YearMonth.of(2012, 11)));
        assertEquals(LocalDate.of(2012, 12, 28), test.atYearMonth(YearMonth.of(2012, 12)));
    }

    @Test
    public void test_atYearMonth_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.atYearMonth((YearMonth) null));
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        // For every pair of day-of-month values, compareTo must agree with the natural ordering of the ints.
        for (int leftValue = 1; leftValue <= MAX_LENGTH; leftValue++) {
            DayOfMonth left = DayOfMonth.of(leftValue);
            for (int rightValue = 1; rightValue <= MAX_LENGTH; rightValue++) {
                DayOfMonth right = DayOfMonth.of(rightValue);
                if (leftValue < rightValue) {
                    assertEquals(true, left.compareTo(right) < 0);
                    assertEquals(true, right.compareTo(left) > 0);
                } else if (leftValue > rightValue) {
                    assertEquals(true, left.compareTo(right) > 0);
                    assertEquals(true, right.compareTo(left) < 0);
                } else {
                    assertEquals(0, left.compareTo(right));
                    assertEquals(0, right.compareTo(left));
                }
            }
        }
    }

    @Test
    public void test_compareTo_nullDayOfMonth() {
        DayOfMonth dom = null;
        DayOfMonth test = DayOfMonth.of(1);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> test.compareTo(dom));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        // Each day-of-month value forms its own equality group; different values are never equal.
        EqualsTester equalsTester = new EqualsTester();
        for (int dayOfMonth = 1; dayOfMonth <= MAX_LENGTH; dayOfMonth++) {
            equalsTester.addEqualityGroup(DayOfMonth.of(dayOfMonth), DayOfMonth.of(dayOfMonth));
        }
        equalsTester.testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        for (int dayOfMonth = 1; dayOfMonth <= MAX_LENGTH; dayOfMonth++) {
            DayOfMonth test = DayOfMonth.of(dayOfMonth);
            assertEquals("DayOfMonth:" + dayOfMonth, test.toString());
        }
    }

    //-----------------------------------------------------------------------
    // now(Clock)
    //-----------------------------------------------------------------------
    @Test
    public void test_now_clock() {
        // A clock fixed to each day in January 2008 must report that day-of-month.
        for (int dayOfMonth = 1; dayOfMonth <= 31; dayOfMonth++) {  // Jan
            Instant instant = LocalDate.of(2008, 1, dayOfMonth).atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);
            assertEquals(dayOfMonth, DayOfMonth.now(clock).getValue());
        }
    }

}
