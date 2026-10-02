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

    private static final Month[] MONTHS = {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
        JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    };
    private static final int[] MONTH_LENGTHS_2007 = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] FIRST_THREE_MONTH_LENGTHS_2008 = {31, 29, 31};
    private static final int[] DAYS_FROM_31_BY_MONTH = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] DAYS_FROM_28_BY_MONTH = {28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28};

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
        assertDayOfMonthMatchesDateWalk(LocalDate.of(2007, 1, 1), MONTH_LENGTHS_2007);
    }

    @Test
    public void test_from_TemporalAccessor_leapYear() {
        assertDayOfMonthMatchesDateWalk(LocalDate.of(2008, 1, 1), FIRST_THREE_MONTH_LENGTHS_2008);
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
        DayOfMonth test = DayOfMonth.of(31);
        boolean[] expected = {true, false, true, false, true, false, true, true, false, true, false, true};
        assertValidYearMonthResults(test, 2012, expected);
    }

    @Test
    public void test_isValidYearMonth_30() {
        DayOfMonth test = DayOfMonth.of(30);
        boolean[] expected = {true, false, true, true, true, true, true, true, true, true, true, true};
        assertValidYearMonthResults(test, 2012, expected);
    }

    @Test
    public void test_isValidYearMonth_29() {
        DayOfMonth test = DayOfMonth.of(29);
        boolean[] expected = {true, true, true, true, true, true, true, true, true, true, true, true};
        assertValidYearMonthResults(test, 2012, expected);
        assertEquals(false, test.isValidYearMonth(YearMonth.of(2011, 2)));
    }

    @Test
    public void test_isValidYearMonth_28() {
        DayOfMonth test = DayOfMonth.of(28);
        boolean[] expected = {true, true, true, true, true, true, true, true, true, true, true, true};
        assertValidYearMonthResults(test, 2012, expected);
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
        LocalDate base = LocalDate.of(2007, 1, 1);
        LocalDate expected = base;
        for (int i = 1; i <= MAX_LENGTH; i++) {
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
        assertAtMonthWithMonthEnum(DayOfMonth.of(31), DAYS_FROM_31_BY_MONTH);
    }

    @Test
    public void test_atMonth_Month_28() {
        assertAtMonthWithMonthEnum(DayOfMonth.of(28), DAYS_FROM_28_BY_MONTH);
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
        assertAtMonthWithMonthNumber(DayOfMonth.of(31), DAYS_FROM_31_BY_MONTH);
    }

    @Test
    public void test_atMonth_int_28() {
        assertAtMonthWithMonthNumber(DayOfMonth.of(28), DAYS_FROM_28_BY_MONTH);
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
        assertAtYearMonth(test, 2012, DAYS_FROM_31_BY_MONTH);
        assertEquals(LocalDate.of(2011, 2, 28), test.atYearMonth(YearMonth.of(2011, 2)));
    }

    @Test
    public void test_atYearMonth_28() {
        assertAtYearMonth(DayOfMonth.of(28), 2012, DAYS_FROM_28_BY_MONTH);
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
                    assertEquals(true, a.compareTo(b) < 0);
                    assertEquals(true, b.compareTo(a) > 0);
                } else if (i > j) {
                    assertEquals(true, a.compareTo(b) > 0);
                    assertEquals(true, b.compareTo(a) < 0);
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
        for (int i = 1; i <= 31; i++) {
            Instant instant = LocalDate.of(2008, 1, i).atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);
            assertEquals(i, DayOfMonth.now(clock).getValue());
        }
    }

    private static void assertDayOfMonthMatchesDateWalk(LocalDate startDate, int... monthLengths) {
        LocalDate date = startDate;
        for (int monthLength : monthLengths) {
            for (int day = 1; day <= monthLength; day++) {
                assertEquals(day, DayOfMonth.from(date).getValue());
                date = date.plusDays(1);
            }
        }
    }

    private static void assertValidYearMonthResults(DayOfMonth test, int year, boolean[] expectedByMonth) {
        for (int month = 1; month <= expectedByMonth.length; month++) {
            assertEquals(expectedByMonth[month - 1], test.isValidYearMonth(YearMonth.of(year, month)));
        }
    }

    private static void assertAtMonthWithMonthEnum(DayOfMonth test, int[] expectedDaysByMonth) {
        for (int index = 0; index < MONTHS.length; index++) {
            assertEquals(MonthDay.of(index + 1, expectedDaysByMonth[index]), test.atMonth(MONTHS[index]));
        }
    }

    private static void assertAtMonthWithMonthNumber(DayOfMonth test, int[] expectedDaysByMonth) {
        for (int month = 1; month <= expectedDaysByMonth.length; month++) {
            assertEquals(MonthDay.of(month, expectedDaysByMonth[month - 1]), test.atMonth(month));
        }
    }

    private static void assertAtYearMonth(DayOfMonth test, int year, int[] expectedDaysByMonth) {
        for (int month = 1; month <= expectedDaysByMonth.length; month++) {
            YearMonth yearMonth = YearMonth.of(year, month);
            assertEquals(LocalDate.of(year, month, expectedDaysByMonth[month - 1]), test.atYearMonth(yearMonth));
        }
    }

}
