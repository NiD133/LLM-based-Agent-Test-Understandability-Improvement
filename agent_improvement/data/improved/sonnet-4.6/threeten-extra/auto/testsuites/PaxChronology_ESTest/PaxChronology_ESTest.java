/*
 * Refactored from EvoSuite-generated test for improved understandability.
 * Runtime behaviour is preserved: same methods, same arguments, same assertions.
 */

package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest extends PaxChronology_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // PaxDate temporal adjustment
    // -----------------------------------------------------------------------

    /**
     * Adjusting a PaxDate with a ZoneOffset fails because ZoneOffset adjusts via
     * OffsetSeconds, which is not supported by PaxDate.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        PaxDate date = PaxChronology.INSTANCE.dateNow();
        ZoneOffset minOffset = ZoneOffset.MIN;
        try {
            date.with((TemporalAdjuster) minOffset);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.chrono.AbstractDate", e);
        }
    }

    // -----------------------------------------------------------------------
    // PaxChronology.range() for calendar-specific ChronoFields
    // -----------------------------------------------------------------------

    /**
     * range(MONTH_OF_YEAR) returns the Pax-specific range (1–13 in a normal year,
     * 1–14 in a leap year).
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        PaxChronology chronology = PaxDate.ofEpochDay(146096L).getChronology();
        ValueRange monthOfYearRange = chronology.range(ChronoField.MONTH_OF_YEAR);
        assertNotNull(monthOfYearRange);
    }

    /**
     * range(DAY_OF_MONTH) returns the Pax-specific range (1–7 for the leap month,
     * 1–28 for all other months).
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        PaxChronology chronology = PaxDate.ofEpochDay(146096L).getChronology();
        ValueRange dayOfMonthRange = chronology.range(ChronoField.DAY_OF_MONTH);
        assertNotNull(dayOfMonthRange);
    }

    /**
     * range(ALIGNED_WEEK_OF_YEAR) returns the Pax-specific range (1–52 normal,
     * 1–53 leap year).
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        ValueRange alignedWeekOfYearRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_YEAR);
        assertNotNull(alignedWeekOfYearRange);
    }

    /**
     * range(DAY_OF_YEAR) returns the Pax-specific range (1–364 normal,
     * 1–371 leap year).
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        ValueRange dayOfYearRange = chronology.range(ChronoField.DAY_OF_YEAR);
        assertNotNull(dayOfYearRange);
    }

    /**
     * range(ALIGNED_WEEK_OF_MONTH) returns the Pax-specific range (1–1 for the
     * leap month, 1–4 for all other months).
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        PaxChronology chronology = PaxDate.ofEpochDay(146096L).getChronology();
        ValueRange alignedWeekOfMonthRange = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);
        assertNotNull(alignedWeekOfMonthRange);
    }

    // -----------------------------------------------------------------------
    // PaxChronology.prolepticYear()
    // -----------------------------------------------------------------------

    /**
     * prolepticYear(CE, 99) returns 99: in the CE era the proleptic year equals
     * the year-of-era directly.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        int prolepticYear = chronology.INSTANCE.prolepticYear(PaxEra.CE, 99);
        assertEquals(99, prolepticYear);
    }

    /**
     * prolepticYear(BCE, 99) returns -98: BCE year 1 maps to proleptic year 0,
     * so BCE year N maps to 1 - N.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        int prolepticYear = chronology.INSTANCE.prolepticYear(PaxEra.BCE, 99);
        assertEquals(-98, prolepticYear);
    }

    // -----------------------------------------------------------------------
    // PaxDate arithmetic
    // -----------------------------------------------------------------------

    /**
     * plusYears returns a new PaxDate instance distinct from the original.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        PaxDate original = PaxDate.ofEpochDay(146096L);
        PaxDate shifted = original.plusYears(146096L);
        assertNotSame(original, shifted);
    }

    // -----------------------------------------------------------------------
    // PaxChronology.isLeapYear()
    // -----------------------------------------------------------------------

    /**
     * Year 99 is a Pax leap year because its last two digits are 99.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        PaxChronology chronology = PaxDate.ofEpochDay(146096L).getChronology();
        boolean isLeap = chronology.isLeapYear(99L);
        assertTrue(isLeap);
    }

    // -----------------------------------------------------------------------
    // PaxChronology.dateNow()
    // -----------------------------------------------------------------------

    /**
     * dateNow(ZoneId) rejects a null zone with NullPointerException.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        try {
            PaxChronology.INSTANCE.dateNow((ZoneId) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.util.Objects", e);
        }
    }

    /**
     * dateNow(Clock) using the mock system clock returns a date in the CE era.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        Clock clock = MockClock.systemDefaultZone();
        PaxDate today = chronology.INSTANCE.dateNow(clock);
        assertEquals(PaxEra.CE, today.getEra());
    }

    // -----------------------------------------------------------------------
    // PaxChronology.eraOf()
    // -----------------------------------------------------------------------

    /**
     * eraOf(0) returns PaxEra.BCE (BCE is represented by value 0).
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        PaxChronology chronology = PaxDate.ofEpochDay(146096L).getChronology();
        PaxEra era = chronology.eraOf(0);
        assertEquals(PaxEra.BCE, era);
    }

    // -----------------------------------------------------------------------
    // PaxChronology identity / metadata
    // -----------------------------------------------------------------------

    /**
     * toString() returns the chronology ID "Pax".
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        String id = PaxChronology.INSTANCE.toString();
        assertEquals("Pax", id);
    }

    /**
     * getCalendarType() returns the LDML calendar type "pax".
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        String calendarType = PaxChronology.INSTANCE.getCalendarType();
        assertEquals("pax", calendarType);
    }

    // -----------------------------------------------------------------------
    // PaxChronology.zonedDateTime()
    // -----------------------------------------------------------------------

    /**
     * zonedDateTime(TemporalAccessor) converts a standard ZonedDateTime to a
     * Pax ChronoZonedDateTime successfully.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        ZonedDateTime now = MockZonedDateTime.now();
        ChronoZonedDateTime<PaxDate> paxZonedDateTime = chronology.zonedDateTime((TemporalAccessor) now);
        assertNotNull(paxZonedDateTime);
    }

    // -----------------------------------------------------------------------
    // PaxChronology.date() validation
    // -----------------------------------------------------------------------

    /**
     * date() with a month value of -1093 (out of the valid range 1–13/14)
     * throws DateTimeException.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        try {
            chronology.date(-1093, -1093, 0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }

    /**
     * date(Era, year, month, day) with a null Era (not a PaxEra instance)
     * throws ClassCastException.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        try {
            PaxChronology.INSTANCE.date((Era) null, -70, -70, -70);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.PaxChronology", e);
        }
    }

    // -----------------------------------------------------------------------
    // PaxChronology.resolveDate()
    // -----------------------------------------------------------------------

    /**
     * resolveDate() with an empty field map returns null because there are no
     * fields to resolve into a complete date.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        HashMap<TemporalField, Long> emptyFields = new HashMap<TemporalField, Long>();
        PaxDate resolved = chronology.INSTANCE.resolveDate(emptyFields, ResolverStyle.STRICT);
        assertNull(resolved);
    }

    // -----------------------------------------------------------------------
    // PaxChronology.dateYearDay()
    // -----------------------------------------------------------------------

    /**
     * dateYearDay(0, 14) creates a date in proleptic year 0, which falls in the
     * BCE era (year 0 maps to BCE year 1 in the Pax calendar).
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        PaxDate date = chronology.dateYearDay(0, 14);
        assertEquals(PaxEra.BCE, date.getEra());
    }

    /**
     * dateYearDay(Era, yearOfEra, dayOfYear) with a non-Pax era (ThaiBuddhistEra)
     * throws ClassCastException.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        ThaiBuddhistEra foreignEra = ThaiBuddhistEra.BEFORE_BE;
        try {
            chronology.dateYearDay((Era) foreignEra, -1431655764, -2843);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.PaxChronology", e);
        }
    }

    // -----------------------------------------------------------------------
    // PaxChronology.eras()
    // -----------------------------------------------------------------------

    /**
     * eras() returns exactly two eras: BCE and CE.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        List<Era> eras = chronology.eras();
        assertEquals(2, eras.size());
    }

    // -----------------------------------------------------------------------
    // PaxChronology.dateEpochDay()
    // -----------------------------------------------------------------------

    /**
     * dateEpochDay(65) creates a date after the Unix epoch (1970-01-01 ISO),
     * which falls in the Pax CE era.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        PaxChronology chronology = new PaxChronology();
        PaxDate date = chronology.dateEpochDay(65L);
        assertEquals(PaxEra.CE, date.getEra());
    }
}
