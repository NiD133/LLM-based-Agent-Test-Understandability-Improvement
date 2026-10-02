/*
 * Improved from EvoSuite-generated test for Quarter.java
 * Refactored for understandability: descriptive test names, clear variable names,
 * and logical grouping by feature.
 */

package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;
import org.threeten.extra.Quarter;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest extends Quarter_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // adjustInto
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void adjustInto_nonIsoDate_throwsDateTimeException() throws Throwable {
        Quarter q1 = Quarter.Q1;
        HijrahDate hijrahDate = MockHijrahDate.now();
        // Quarter.adjustInto only supports ISO calendar; Hijrah is non-ISO
        try {
            q1.adjustInto(hijrahDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Adjustment only supported on ISO date-time
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    @Test(timeout = 4000)
    public void adjustInto_isoDateTime_returnsAdjustedTemporal() throws Throwable {
        Quarter q4 = Quarter.Q4;
        LocalDateTime now = MockLocalDateTime.now();
        // Adjusting into Q4 changes the quarter-of-year, so the result differs from now
        Temporal adjusted = q4.adjustInto(now);
        assertFalse(adjusted.equals(now));
    }

    // -----------------------------------------------------------------------
    // query
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void query_nullQuery_throwsNullPointerException() throws Throwable {
        Quarter q4 = Quarter.of(4);
        try {
            q4.query((TemporalQuery<Object>) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
        }
    }

    // -----------------------------------------------------------------------
    // firstMonth
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void firstMonth_Q1_returnsJanuary() throws Throwable {
        Month firstMonthOfQ1 = Quarter.Q1.firstMonth();
        assertEquals(Month.JANUARY, firstMonthOfQ1);
    }

    @Test(timeout = 4000)
    public void firstMonth_Q2_returnsApril() throws Throwable {
        Month firstMonthOfQ2 = Quarter.Q2.firstMonth();
        assertEquals(Month.APRIL, firstMonthOfQ2);
    }

    @Test(timeout = 4000)
    public void firstMonth_Q3_returnsJuly() throws Throwable {
        Month firstMonthOfQ3 = Quarter.Q3.firstMonth();
        assertEquals(Month.JULY, firstMonthOfQ3);
    }

    @Test(timeout = 4000)
    public void firstMonth_Q4_returnsOctober() throws Throwable {
        Month firstMonthOfQ4 = Quarter.Q4.firstMonth();
        assertEquals(Month.OCTOBER, firstMonthOfQ4);
    }

    // -----------------------------------------------------------------------
    // length
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void length_Q1_standardYear_returns90() throws Throwable {
        int days = Quarter.Q1.length(false);
        assertEquals(90, days);
    }

    @Test(timeout = 4000)
    public void length_Q1_leapYear_returns91() throws Throwable {
        int days = Quarter.Q1.length(true);
        assertEquals(91, days);
    }

    @Test(timeout = 4000)
    public void length_Q2_leapYear_returns91() throws Throwable {
        int days = Quarter.Q2.length(true);
        assertEquals(91, days);
    }

    @Test(timeout = 4000)
    public void length_Q3_leapYear_returns92() throws Throwable {
        int days = Quarter.Q3.length(true);
        assertEquals(92, days);
    }

    // -----------------------------------------------------------------------
    // getLong / get / range — unsupported ChronoField handling
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void getLong_unsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Quarter q4 = Quarter.Q4;
        ChronoField secondOfDay = ChronoField.SECOND_OF_DAY;
        // SECOND_OF_DAY is not supported by Quarter; delegating via getFrom propagates the exception
        try {
            secondOfDay.getFrom(q4);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            //
            // Unsupported field: SecondOfDay
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    @Test(timeout = 4000)
    public void getLong_nullField_throwsNullPointerException() throws Throwable {
        Quarter q2 = Quarter.Q2;
        try {
            q2.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    @Test(timeout = 4000)
    public void get_unsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Quarter q2 = Quarter.Q2;
        ChronoField clockHourOfAmPm = ChronoField.CLOCK_HOUR_OF_AMPM;
        try {
            q2.get(clockHourOfAmPm);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            //
            // Unsupported field: ClockHourOfAmPm
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    @Test(timeout = 4000)
    public void get_nullField_throwsNullPointerException() throws Throwable {
        Quarter q2 = Quarter.Q2;
        try {
            q2.get((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // field
            //
            verifyException("java.util.Objects", e);
        }
    }

    @Test(timeout = 4000)
    public void range_unsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Quarter q1 = Quarter.Q1;
        ChronoField amPmOfDay = ChronoField.AMPM_OF_DAY;
        try {
            q1.range(amPmOfDay);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            //
            // Unsupported field: AmPmOfDay
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    // -----------------------------------------------------------------------
    // isSupported
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isSupported_nullField_returnsFalse() throws Throwable {
        boolean supported = Quarter.Q2.isSupported((TemporalField) null);
        assertFalse(supported);
    }

    @Test(timeout = 4000)
    public void isSupported_chronoFieldYearOfEra_returnsFalse() throws Throwable {
        Quarter q4 = Quarter.of(4);
        assertEquals(Quarter.Q4, q4);

        boolean supported = q4.isSupported(ChronoField.YEAR_OF_ERA);
        assertFalse(supported);
    }

    // -----------------------------------------------------------------------
    // from
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void from_zoneOffset_throwsDateTimeException() throws Throwable {
        ZoneOffset minOffset = ZoneOffset.MIN;
        try {
            Quarter.from(minOffset);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Unable to obtain Quarter from TemporalAccessor: -18:00 of type java.time.ZoneOffset
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    @Test(timeout = 4000)
    public void from_quarterInstance_returnsSameInstance() throws Throwable {
        Quarter q4 = Quarter.Q4;
        Quarter result = Quarter.from(q4);
        assertSame(result, q4);
    }

    @Test(timeout = 4000)
    public void from_monthJuly_returnsQ3() throws Throwable {
        Month july = Month.JULY;
        Quarter quarter = Quarter.from(july);
        assertEquals(Quarter.Q3, quarter);
    }

    @Test(timeout = 4000)
    public void from_japaneseDate_returnsExpectedQuarter() throws Throwable {
        ZoneOffset minOffset = ZoneOffset.MIN;
        Clock clock = MockClock.tickSeconds(minOffset);
        JapaneseDate japaneseDate = MockJapaneseDate.now(clock);
        Quarter quarter = Quarter.from(japaneseDate);
        assertEquals(Quarter.Q1, quarter);
    }

    // -----------------------------------------------------------------------
    // of / ofMonth
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void of_invalidValue6_throwsDateTimeException() throws Throwable {
        try {
            Quarter.of(6);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid value for Quarter: 6
            //
            verifyException("org.threeten.extra.Quarter", e);
        }
    }

    @Test(timeout = 4000)
    public void ofMonth_april_returnsQ2() throws Throwable {
        // Month 4 = April, which falls in Q2
        Quarter quarter = Quarter.ofMonth(4);
        assertEquals(Quarter.Q2, quarter);
    }

    // -----------------------------------------------------------------------
    // minus
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void minus_zeroQuarters_returnsSameQuarter() throws Throwable {
        Quarter q4 = Quarter.Q4;
        Quarter result = q4.minus(0L);
        assertEquals(Quarter.Q4, result);
    }

    // -----------------------------------------------------------------------
    // getDisplayName
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void getDisplayName_shortStyleJapanLocale_returnsQ4String() throws Throwable {
        Quarter q4 = Quarter.of(4);
        String displayName = q4.getDisplayName(TextStyle.SHORT, Locale.JAPAN);
        assertEquals("Q4", displayName);
    }
}
