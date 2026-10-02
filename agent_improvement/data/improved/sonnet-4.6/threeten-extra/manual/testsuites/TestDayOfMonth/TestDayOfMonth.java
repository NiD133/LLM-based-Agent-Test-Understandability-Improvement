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
import static org.junit.jupiter.api.Assertions.assertNull;
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
        for (int i = 1; i <= MAX_LENGTH; i++) {
            DayOfMonth test = DayOfMonth.of(i);
            assertEquals(i, test.getValue());
            assertSame(test, DayOfMonth.of(i));
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
        LocalDate date = LocalDate.of(2007, 1, 1);
        for (int i = 1; i <= 31; i++) {  // Jan
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 28; i++) {  // Feb
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // Mar
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 30; i++) {  // Apr
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // May
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 30; i++) {  // Jun
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // Jul
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // Aug
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 30; i++) {  // Sep
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // Oct
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 30; i++) {  // Nov
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // Dec
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
    }

    @Test
    public void test_from_TemporalAccessor_leapYear() {
        LocalDate date = LocalDate.of(2008, 1, 1);
        for (int i = 1; i <= 31; i++) {  // Jan
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 29; i++) {  // Feb
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        for (int i = 1; i <= 31; i++) {  // Mar
            assertEquals(i, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
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
        assertFalse(TEST.isSupported((TemporalField) null));
        assertFalse(TEST.isSupported(NANO_OF_SECOND));
        assertFalse(TEST.isSupported(NANO_OF_DAY));
        assertFalse(TEST.isSupported(MICRO_OF_SECOND));
        assertFalse(TEST.isSupported(MICRO_OF_DAY));
        assertFalse(TEST.isSupported(MILLI_OF_SECOND));
        assertFalse(TEST.isSupported(MILLI_OF_DAY));
        assertFalse(TEST.isSupported(SECOND_OF_MINUTE));
        assertFalse(TEST.isSupported(SECOND_OF_DAY));
        assertFalse(TEST.isSupported(MINUTE_OF_HOUR));
        assertFalse(TEST.isSupported(MINUTE_OF_DAY));
        assertFalse(TEST.isSupported(HOUR_OF_AMPM));
        assertFalse(TEST.isSupported(CLOCK_HOUR_OF_AMPM));
        assertFalse(TEST.isSupported(HOUR_OF_DAY));
        assertFalse(TEST.isSupported(CLOCK_HOUR_OF_DAY));
        assertFalse(TEST.isSupported(AMPM_OF_DAY));
        assertFalse(TEST.isSupported(DAY_OF_WEEK));
        assertFalse(TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertFalse(TEST.isSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertTrue(TEST.isSupported(DAY_OF_MONTH));
        assertFalse(TEST.isSupported(DAY_OF_YEAR));
        assertFalse(TEST.isSupported(EPOCH_DAY));
        assertFalse(TEST.isSupported(ALIGNED_WEEK_OF_MONTH));
        assertFalse(TEST.isSupported(ALIGNED_WEEK_OF_YEAR));
        assertFalse(TEST.isSupported(MONTH_OF_YEAR));
        assertFalse(TEST.isSupported(PROLEPTIC_MONTH));
        assertFalse(TEST.isSupported(YEAR_OF_ERA));
        assertFalse(TEST.isSupported(YEAR));
        assertFalse(TEST.isSupported(ERA));
        assertFalse(TEST.isSupported(INSTANT_SECONDS));
        assertFalse(TEST.isSupported(OFFSET_SECONDS));
        assertFalse(TEST.isSupported(IsoFields.DAY_OF_QUARTER));
        assertTrue(TEST.isSupported(TestingField.INSTANCE));
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
        DayOfMonth test = DayOfMonth.of(31);
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 1)));   // Jan has 31 days
        assertFalse(test.isValidYearMonth(YearMonth.of(2012, 2)));  // Feb has 29 days (2012 is leap)
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 3)));   // Mar has 31 days
        assertFalse(test.isValidYearMonth(YearMonth.of(2012, 4)));  // Apr has 30 days
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 5)));   // May has 31 days
        assertFalse(test.isValidYearMonth(YearMonth.of(2012, 6)));  // Jun has 30 days
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 7)));   // Jul has 31 days
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 8)));   // Aug has 31 days
        assertFalse(test.isValidYearMonth(YearMonth.of(2012, 9)));  // Sep has 30 days
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 10)));  // Oct has 31 days
        assertFalse(test.isValidYearMonth(YearMonth.of(2012, 11))); // Nov has 30 days
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 12)));  // Dec has 31 days
    }

    @Test
    public void test_isValidYearMonth_30() {
        DayOfMonth test = DayOfMonth.of(30);
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 1)));
        assertFalse(test.isValidYearMonth(YearMonth.of(2012, 2)));  // Feb has only 29 days (2012 is leap)
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 3)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 4)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 5)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 6)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 7)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 8)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 9)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 10)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 11)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 12)));
    }

    @Test
    public void test_isValidYearMonth_29() {
        DayOfMonth test = DayOfMonth.of(29);
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 1)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 2)));   // 2012 is a leap year
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 3)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 4)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 5)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 6)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 7)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 8)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 9)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 10)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 11)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 12)));
        assertFalse(test.isValidYearMonth(YearMonth.of(2011, 2)));  // 2011 is not a leap year
    }

    @Test
    public void test_isValidYearMonth_28() {
        DayOfMonth test = DayOfMonth.of(28);
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 1)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 2)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 3)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 4)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 5)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 6)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 7)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 8)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 9)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 10)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 11)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, 12)));
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
        assertNull(TEST.query(TemporalQueries.localDate()));
        assertNull(TEST.query(TemporalQueries.localTime()));
        assertNull(TEST.query(TemporalQueries.offset()));
        assertEquals(ChronoUnit.DAYS, TEST.query(TemporalQueries.precision()));
        assertNull(TEST.query(TemporalQueries.zone()));
        assertNull(TEST.query(TemporalQueries.zoneId()));
    }

    //-----------------------------------------------------------------------
    // adjustInto(Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto() {
        LocalDate base = LocalDate.of(2007, 1, 1);
        LocalDate expected = base;
        for (int i = 1; i <= MAX_LENGTH; i++) {  // Jan
            Temporal result = DayOfMonth.of(i).adjustInto(base);
            assertEquals(expected, result);
            expected = expected.plusDays(1);
        }
    }

    @Test
    public void test_adjustInto_april31() {
        LocalDate base = LocalDate.of(2007, 4, 1);
        DayOfMonth test = DayOfMonth.of(31);
        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }

    @Test
    public void test_adjustInto_february29_notLeapYear() {
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
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(MonthDay.of(1, 31), test.atMonth(JANUARY));
        assertEquals(MonthDay.of(2, 29), test.atMonth(FEBRUARY));   // clamped to max length of Feb
        assertEquals(MonthDay.of(3, 31), test.atMonth(MARCH));
        assertEquals(MonthDay.of(4, 30), test.atMonth(APRIL));      // clamped to max length of Apr
        assertEquals(MonthDay.of(5, 31), test.atMonth(MAY));
        assertEquals(MonthDay.of(6, 30), test.atMonth(JUNE));       // clamped to max length of Jun
        assertEquals(MonthDay.of(7, 31), test.atMonth(JULY));
        assertEquals(MonthDay.of(8, 31), test.atMonth(AUGUST));
        assertEquals(MonthDay.of(9, 30), test.atMonth(SEPTEMBER));  // clamped to max length of Sep
        assertEquals(MonthDay.of(10, 31), test.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 30), test.atMonth(NOVEMBER));  // clamped to max length of Nov
        assertEquals(MonthDay.of(12, 31), test.atMonth(DECEMBER));
    }

    @Test
    public void test_atMonth_Month_28() {
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
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(MonthDay.of(1, 31), test.atMonth(1));
        assertEquals(MonthDay.of(2, 29), test.atMonth(2));   // clamped to max length of Feb
        assertEquals(MonthDay.of(3, 31), test.atMonth(3));
        assertEquals(MonthDay.of(4, 30), test.atMonth(4));   // clamped to max length of Apr
        assertEquals(MonthDay.of(5, 31), test.atMonth(5));
        assertEquals(MonthDay.of(6, 30), test.atMonth(6));   // clamped to max length of Jun
        assertEquals(MonthDay.of(7, 31), test.atMonth(7));
        assertEquals(MonthDay.of(8, 31), test.atMonth(8));
        assertEquals(MonthDay.of(9, 30), test.atMonth(9));   // clamped to max length of Sep
        assertEquals(MonthDay.of(10, 31), test.atMonth(10));
        assertEquals(MonthDay.of(11, 30), test.atMonth(11)); // clamped to max length of Nov
        assertEquals(MonthDay.of(12, 31), test.atMonth(12));
    }

    @Test
    public void test_atMonth_int_28() {
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
        DayOfMonth test = DayOfMonth.of(31);
        assertEquals(LocalDate.of(2012, 1, 31), test.atYearMonth(YearMonth.of(2012, 1)));
        assertEquals(LocalDate.of(2012, 2, 29), test.atYearMonth(YearMonth.of(2012, 2)));   // clamped: 2012 Feb has 29 days
        assertEquals(LocalDate.of(2012, 3, 31), test.atYearMonth(YearMonth.of(2012, 3)));
        assertEquals(LocalDate.of(2012, 4, 30), test.atYearMonth(YearMonth.of(2012, 4)));   // clamped: Apr has 30 days
        assertEquals(LocalDate.of(2012, 5, 31), test.atYearMonth(YearMonth.of(2012, 5)));
        assertEquals(LocalDate.of(2012, 6, 30), test.atYearMonth(YearMonth.of(2012, 6)));   // clamped: Jun has 30 days
        assertEquals(LocalDate.of(2012, 7, 31), test.atYearMonth(YearMonth.of(2012, 7)));
        assertEquals(LocalDate.of(2012, 8, 31), test.atYearMonth(YearMonth.of(2012, 8)));
        assertEquals(LocalDate.of(2012, 9, 30), test.atYearMonth(YearMonth.of(2012, 9)));   // clamped: Sep has 30 days
        assertEquals(LocalDate.of(2012, 10, 31), test.atYearMonth(YearMonth.of(2012, 10)));
        assertEquals(LocalDate.of(2012, 11, 30), test.atYearMonth(YearMonth.of(2012, 11))); // clamped: Nov has 30 days
        assertEquals(LocalDate.of(2012, 12, 31), test.atYearMonth(YearMonth.of(2012, 12)));
        assertEquals(LocalDate.of(2011, 2, 28), test.atYearMonth(YearMonth.of(2011, 2)));   // clamped: 2011 Feb has 28 days
    }

    @Test
    public void test_atYearMonth_28() {
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
        for (int i = 1; i <= MAX_LENGTH; i++) {
            DayOfMonth a = DayOfMonth.of(i);
            for (int j = 1; j <= MAX_LENGTH; j++) {
                DayOfMonth b = DayOfMonth.of(j);
                if (i < j) {
                    assertTrue(a.compareTo(b) < 0);
                    assertTrue(b.compareTo(a) > 0);
                } else if (i > j) {
                    assertTrue(a.compareTo(b) > 0);
                    assertTrue(b.compareTo(a) < 0);
                } else {
                    assertEquals(0, a.compareTo(b));
                    assertEquals(0, b.compareTo(a));
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
        EqualsTester equalsTester = new EqualsTester();
        for (int i = 1; i <= MAX_LENGTH; i++) {
            equalsTester.addEqualityGroup(DayOfMonth.of(i), DayOfMonth.of(i));
        }
        equalsTester.testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        for (int i = 1; i <= MAX_LENGTH; i++) {
            DayOfMonth a = DayOfMonth.of(i);
            assertEquals("DayOfMonth:" + i, a.toString());
        }
    }

    //-----------------------------------------------------------------------
    // now(Clock)
    //-----------------------------------------------------------------------
    @Test
    public void test_now_clock() {
        for (int i = 1; i <= 31; i++) {  // Jan
            Instant instant = LocalDate.of(2008, 1, i).atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);
            assertEquals(i, DayOfMonth.now(clock).getValue());
        }
    }

}
