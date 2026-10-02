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
import java.time.Year;
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
 * Test DayOfYear.
 */
public class TestDayOfYear {

    private static final Year YEAR_STANDARD = Year.of(2007);
    private static final Year YEAR_LEAP = Year.of(2008);
    private static final int STANDARD_YEAR = 2007;
    private static final int LEAP_YEAR = 2008;
    private static final int STANDARD_YEAR_LENGTH = 365;
    private static final int LEAP_YEAR_LENGTH = 366;
    private static final DayOfYear TEST = DayOfYear.of(12);
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    private static class TestingField implements TemporalField {

        public static final TestingField INSTANCE = new TestingField();

        @Override
        public TemporalUnit getBaseUnit() {
            return ChronoUnit.DAYS;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return ChronoUnit.YEARS;
        }

        @Override
        public ValueRange range() {
            return ValueRange.of(1, 365, 366);
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
            return temporal.isSupported(DAY_OF_YEAR);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            return range();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(DAY_OF_YEAR);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            return (R) temporal.with(DAY_OF_YEAR, newValue);
        }
    }

    private static void assertNotSupported(TemporalField field) {
        assertEquals(false, TEST.isSupported(field));
    }

    private static void assertSupported(TemporalField field) {
        assertEquals(true, TEST.isSupported(field));
    }

    private static void assertDayOfYearFactoryRange(int length) {
        for (int day = 1; day <= length; day++) {
            DayOfYear test = DayOfYear.of(day);
            assertEquals(day, test.getValue());
            assertSame(test, DayOfYear.of(day));
        }
    }

    private static void assertDayOfYearFromDates(LocalDate firstDate, int length) {
        LocalDate date = firstDate;
        for (int day = 1; day <= length; day++) {
            DayOfYear test = DayOfYear.from(date);
            assertEquals(day, test.getValue());
            date = date.plusDays(1);
        }
    }

    private static void assertAdjustIntoFromBase(LocalDate base, LocalDate firstExpected, int length) {
        LocalDate expected = firstExpected;
        for (int day = 1; day <= length; day++) {
            DayOfYear test = DayOfYear.of(day);
            assertEquals(expected, test.adjustInto(base));
            expected = expected.plusDays(1);
        }
    }

    private static void assertAtYear(Year year, LocalDate firstExpected, int length) {
        LocalDate expected = firstExpected;
        for (int day = 1; day <= length; day++) {
            DayOfYear test = DayOfYear.of(day);
            assertEquals(expected, test.atYear(year));
            expected = expected.plusDays(1);
        }
    }

    private static void assertAtYear(int year, LocalDate firstExpected, int length) {
        LocalDate expected = firstExpected;
        for (int day = 1; day <= length; day++) {
            DayOfYear test = DayOfYear.of(day);
            assertEquals(expected, test.atYear(year));
            expected = expected.plusDays(1);
        }
    }

    private static void assertNowWithFixedClock(LocalDate firstDate, int length) {
        LocalDate date = firstDate;
        for (int day = 1; day <= length; day++) {
            Instant instant = date.atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);
            DayOfYear test = DayOfYear.now(clock);
            assertEquals(day, test.getValue());
            date = date.plusDays(1);
        }
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_interfaces() {
        assertTrue(Serializable.class.isAssignableFrom(DayOfYear.class));
        assertTrue(Comparable.class.isAssignableFrom(DayOfYear.class));
        assertTrue(TemporalAdjuster.class.isAssignableFrom(DayOfYear.class));
        assertTrue(TemporalAccessor.class.isAssignableFrom(DayOfYear.class));
    }

    @Test
    public void test_serialization() throws IOException, ClassNotFoundException {
        DayOfYear test = DayOfYear.of(1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()))) {
            assertSame(test, ois.readObject());
        }
    }

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_of_int() {
        assertDayOfYearFactoryRange(LEAP_YEAR_LENGTH);
    }

    @Test
    public void test_of_int_tooLow() {
        assertThrows(DateTimeException.class, () -> DayOfYear.of(0));
    }

    @Test
    public void test_of_int_tooHigh() {
        assertThrows(DateTimeException.class, () -> DayOfYear.of(367));
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_notLeapYear() {
        LocalDate date = LocalDate.of(2007, 1, 1);
        assertDayOfYearFromDates(date, STANDARD_YEAR_LENGTH);
        DayOfYear test = DayOfYear.from(date.plusDays(STANDARD_YEAR_LENGTH));
        assertEquals(1, test.getValue());
    }

    @Test
    public void test_from_TemporalAccessor_leapYear() {
        assertDayOfYearFromDates(LocalDate.of(2008, 1, 1), LEAP_YEAR_LENGTH);
    }

    @Test
    public void test_from_TemporalAccessor_DayOfYear() {
        DayOfYear dom = DayOfYear.of(6);
        assertEquals(dom, DayOfYear.from(dom));
    }

    @Test
    public void test_from_TemporalAccessor_nonIso() {
        LocalDate date = LocalDate.now();
        assertEquals(date.getDayOfYear(), DayOfYear.from(JapaneseDate.from(date)).getValue());
    }

    @Test
    public void test_from_TemporalAccessor_noDerive() {
        assertThrows(DateTimeException.class, () -> DayOfYear.from(LocalTime.NOON));
    }

    @Test
    public void test_from_TemporalAccessor_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> DayOfYear.from((TemporalAccessor) null));
    }

    @Test
    public void test_from_parse_CharSequence() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("D");
        assertEquals(DayOfYear.of(76), formatter.parse("76", DayOfYear::from));
    }

    //-----------------------------------------------------------------------
    // isSupported(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_isSupported() {
        assertNotSupported((TemporalField) null);
        assertNotSupported(NANO_OF_SECOND);
        assertNotSupported(NANO_OF_DAY);
        assertNotSupported(MICRO_OF_SECOND);
        assertNotSupported(MICRO_OF_DAY);
        assertNotSupported(MILLI_OF_SECOND);
        assertNotSupported(MILLI_OF_DAY);
        assertNotSupported(SECOND_OF_MINUTE);
        assertNotSupported(SECOND_OF_DAY);
        assertNotSupported(MINUTE_OF_HOUR);
        assertNotSupported(MINUTE_OF_DAY);
        assertNotSupported(HOUR_OF_AMPM);
        assertNotSupported(CLOCK_HOUR_OF_AMPM);
        assertNotSupported(HOUR_OF_DAY);
        assertNotSupported(CLOCK_HOUR_OF_DAY);
        assertNotSupported(AMPM_OF_DAY);
        assertNotSupported(DAY_OF_WEEK);
        assertNotSupported(ALIGNED_DAY_OF_WEEK_IN_MONTH);
        assertNotSupported(ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertNotSupported(DAY_OF_MONTH);
        assertSupported(DAY_OF_YEAR);
        assertNotSupported(EPOCH_DAY);
        assertNotSupported(ALIGNED_WEEK_OF_MONTH);
        assertNotSupported(ALIGNED_WEEK_OF_YEAR);
        assertNotSupported(MONTH_OF_YEAR);
        assertNotSupported(PROLEPTIC_MONTH);
        assertNotSupported(YEAR_OF_ERA);
        assertNotSupported(YEAR);
        assertNotSupported(ERA);
        assertNotSupported(INSTANT_SECONDS);
        assertNotSupported(OFFSET_SECONDS);
        assertSupported(TestingField.INSTANCE);
    }

    //-----------------------------------------------------------------------
    // range(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_range() {
        assertEquals(DAY_OF_YEAR.range(), TEST.range(DAY_OF_YEAR));
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
        assertEquals(12, TEST.get(DAY_OF_YEAR));
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
        assertEquals(12L, TEST.getLong(DAY_OF_YEAR));
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
    // isValidYear(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_isValidYear_366() {
        DayOfYear test = DayOfYear.of(366);
        assertEquals(false, test.isValidYear(2011));
        assertEquals(true, test.isValidYear(2012));
        assertEquals(false, test.isValidYear(2013));
    }

    @Test
    public void test_isValidYear_365() {
        DayOfYear test = DayOfYear.of(365);
        assertEquals(true, test.isValidYear(2011));
        assertEquals(true, test.isValidYear(2012));
        assertEquals(true, test.isValidYear(2013));
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
    public void test_adjustInto_fromStartOfYear_notLeapYear() {
        assertAdjustIntoFromBase(
                LocalDate.of(2007, 1, 1),
                LocalDate.of(2007, 1, 1),
                STANDARD_YEAR_LENGTH);
    }

    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear() {
        assertAdjustIntoFromBase(
                LocalDate.of(2007, 12, 31),
                LocalDate.of(2007, 1, 1),
                STANDARD_YEAR_LENGTH);
    }

    @Test
    public void test_adjustInto_fromStartOfYear_notLeapYear_day366() {
        LocalDate base = LocalDate.of(2007, 1, 1);
        DayOfYear test = DayOfYear.of(LEAP_YEAR_LENGTH);
        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }

    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear_day366() {
        LocalDate base = LocalDate.of(2007, 12, 31);
        DayOfYear test = DayOfYear.of(LEAP_YEAR_LENGTH);
        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }

    @Test
    public void test_adjustInto_fromStartOfYear_leapYear() {
        assertAdjustIntoFromBase(
                LocalDate.of(2008, 1, 1),
                LocalDate.of(2008, 1, 1),
                LEAP_YEAR_LENGTH);
    }

    @Test
    public void test_adjustInto_fromEndOfYear_leapYear() {
        assertAdjustIntoFromBase(
                LocalDate.of(2008, 12, 31),
                LocalDate.of(2008, 1, 1),
                LEAP_YEAR_LENGTH);
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
    // atYear(Year)
    //-----------------------------------------------------------------------
    @Test
    public void test_atYear_Year_notLeapYear() {
        assertAtYear(YEAR_STANDARD, LocalDate.of(2007, 1, 1), STANDARD_YEAR_LENGTH);
    }

    @Test
    public void test_atYear_fromStartOfYear_notLeapYear_day366() {
        DayOfYear test = DayOfYear.of(LEAP_YEAR_LENGTH);
        assertThrows(DateTimeException.class, () -> test.atYear(YEAR_STANDARD));
    }

    @Test
    public void test_atYear_Year_leapYear() {
        assertAtYear(YEAR_LEAP, LocalDate.of(2008, 1, 1), LEAP_YEAR_LENGTH);
    }

    @Test
    public void test_atYear_Year_nullYear() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.atYear((Year) null));
    }

    //-----------------------------------------------------------------------
    // atYear(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_atYear_int_notLeapYear() {
        assertAtYear(STANDARD_YEAR, LocalDate.of(2007, 1, 1), STANDARD_YEAR_LENGTH);
    }

    @Test
    public void test_atYear_int_fromStartOfYear_notLeapYear_day366() {
        DayOfYear test = DayOfYear.of(LEAP_YEAR_LENGTH);
        assertThrows(DateTimeException.class, () -> test.atYear(2007));
    }

    @Test
    public void test_atYear_int_leapYear() {
        assertAtYear(LEAP_YEAR, LocalDate.of(2008, 1, 1), LEAP_YEAR_LENGTH);
    }

    @Test
    public void test_atYear_int_invalidDay() {
        assertThrows(DateTimeException.class, () -> TEST.atYear(Year.MIN_VALUE - 1));
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            DayOfYear a = DayOfYear.of(i);
            for (int j = 1; j <= LEAP_YEAR_LENGTH; j++) {
                DayOfYear b = DayOfYear.of(j);
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
    public void test_compareTo_nullDayOfYear() {
        DayOfYear doy = null;
        DayOfYear test = DayOfYear.of(1);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> test.compareTo(doy));
    }

    //-----------------------------------------------------------------------
    // equals() / hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            equalsTester.addEqualityGroup(DayOfYear.of(i), DayOfYear.of(i));
        }
        equalsTester.testEquals();
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            DayOfYear a = DayOfYear.of(i);
            assertEquals("DayOfYear:" + i, a.toString());
        }
    }

    //-----------------------------------------------------------------------
    // now(Clock)
    //-----------------------------------------------------------------------
    @Test
    public void test_now_clock_notLeapYear() {
        assertNowWithFixedClock(LocalDate.of(2007, 1, 1), STANDARD_YEAR_LENGTH);
    }

    @Test
    public void test_now_clock_leapYear() {
        assertNowWithFixedClock(LocalDate.of(2008, 1, 1), LEAP_YEAR_LENGTH);
    }

}
