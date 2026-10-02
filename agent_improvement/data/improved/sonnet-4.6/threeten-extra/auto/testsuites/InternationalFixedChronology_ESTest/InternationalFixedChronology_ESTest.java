package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;
import org.threeten.extra.chrono.EthiopicDate;
import org.threeten.extra.chrono.InternationalFixedChronology;
import org.threeten.extra.chrono.InternationalFixedDate;
import org.threeten.extra.chrono.InternationalFixedEra;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest extends InternationalFixedChronology_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // prolepticYear
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void prolepticYear_withCeEraAndPositiveYear_returnsYearUnchanged() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedEra ceEra = InternationalFixedEra.CE;
        int result = chronology.prolepticYear(ceEra, 2099);
        assertEquals(2099, result);
    }

    // -----------------------------------------------------------------------
    // range(ChronoField)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void range_hourOfDayField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.HOUR_OF_DAY);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_yearField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;
        ValueRange range = chronology.range(ChronoField.YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_yearOfEraField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;
        ValueRange range = chronology.range(ChronoField.YEAR_OF_ERA);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_prolepticMonthField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.PROLEPTIC_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_monthOfYearField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.MONTH_OF_YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_dayOfMonthField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.DAY_OF_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_alignedWeekOfYearField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.INSTANCE.range(ChronoField.ALIGNED_WEEK_OF_YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_alignedWeekOfMonthField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_dayOfWeekField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.DAY_OF_WEEK);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_alignedDayOfWeekInMonthField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void range_alignedDayOfWeekInYearField_returnsNonNullRange() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ValueRange range = chronology.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertNotNull(range);
    }

    // -----------------------------------------------------------------------
    // InternationalFixedDate.with(TemporalField, long) — validation
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void withEraField_valueOutOfRange_throwsDateTimeException() throws Throwable {
        ZoneOffset utc = ZoneOffset.UTC;
        InternationalFixedDate date = InternationalFixedDate.now((ZoneId) utc);
        // ERA must be 1; 308 is out of [1, 1]
        try {
            date.with((TemporalField) ChronoField.ERA, 308L);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }

    @Test(timeout = 4000)
    public void withDayOfYearField_valueTooLarge_throwsDateTimeException() throws Throwable {
        ZoneOffset utc = ZoneOffset.UTC;
        InternationalFixedDate date = InternationalFixedDate.now((ZoneId) utc);
        // DAY_OF_YEAR max is 365/366; 1820 is far out of range
        try {
            date.with((TemporalField) ChronoField.DAY_OF_YEAR, 1820L);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }

    // -----------------------------------------------------------------------
    // date(int, int, int) and dateYearDay(int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void date_prolepticYearMonthDay_returnsCorrectEpochDay() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedDate date = chronology.date(12, 12, 12);
        assertEquals((-714825L), date.toEpochDay());
    }

    @Test(timeout = 4000)
    public void dateYearDay_thenAdjustWithEthiopicDate_preservesEpochDayAndYearLength() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedDate ifcDate = chronology.dateYearDay(7, 7);
        EthiopicDate ethiopicDate = EthiopicDate.from(ifcDate);
        InternationalFixedDate adjusted = ifcDate.with((TemporalAdjuster) ethiopicDate);
        assertEquals((-716965L), adjusted.toEpochDay());
        assertEquals(365, adjusted.lengthOfYear());
    }

    // -----------------------------------------------------------------------
    // dateEpochDay — boundary validation
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void dateEpochDay_epochDayForYear0_throwsDateTimeException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        // Epoch day -719528 maps to year 0 (BCE), which is out of the valid [1, 1_000_000] range
        try {
            chronology.dateEpochDay((-719528L));
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }

    // -----------------------------------------------------------------------
    // dateYearDay(Era, int, int) — invalid era type
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void dateYearDay_withIncompatibleEraType_throwsClassCastException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        // ThaiBuddhistEra is not an InternationalFixedEra; cast must fail
        ThaiBuddhistEra incompatibleEra = ThaiBuddhistEra.BEFORE_BE;
        try {
            chronology.INSTANCE.dateYearDay((Era) incompatibleEra, 4, 4);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", e);
        }
    }

    // -----------------------------------------------------------------------
    // date(Era, int, int, int) — invalid era type
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void date_withJapaneseEra_throwsClassCastException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        // JapaneseEra is incompatible with InternationalFixedEra
        JapaneseEra japaneseEra = JapaneseEra.SHOWA;
        try {
            chronology.date((Era) japaneseEra, (-1), (-1), (-1));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            verifyException("org.threeten.extra.chrono.InternationalFixedChronology", e);
        }
    }

    // -----------------------------------------------------------------------
    // eraOf
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void eraOf_invalidEraValue_throwsDateTimeException() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        // Only value 1 (CE) is valid; -2771 is invalid
        try {
            chronology.INSTANCE.eraOf((-2771));
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.chrono.InternationalFixedEra", e);
        }
    }

    // -----------------------------------------------------------------------
    // eras
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void eras_returnsNonEmptyList() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        List<Era> eras = chronology.INSTANCE.eras();
        assertFalse(eras.isEmpty());
    }

    // -----------------------------------------------------------------------
    // isLeapYear
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void isLeapYear_centuryNotDivisibleBy400_returnsFalse() throws Throwable {
        ZoneOffset minOffset = ZoneOffset.MIN;
        InternationalFixedDate date = InternationalFixedDate.now((ZoneId) minOffset);
        InternationalFixedChronology chronology = date.getChronology();
        // Year 100 is divisible by 4 but also by 100 (and not by 400) → not a leap year
        boolean isLeap = chronology.isLeapYear(100L);
        assertEquals(365, date.lengthOfYear());
        assertFalse(isLeap);
    }

    // -----------------------------------------------------------------------
    // getCalendarType
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void getCalendarType_returnsNull() throws Throwable {
        // IFC has no CLDR/LDML identifier, so getCalendarType() is defined to return null
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        String calendarType = chronology.getCalendarType();
        assertNull(calendarType);
    }

    // -----------------------------------------------------------------------
    // dateNow variants
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void dateNow_defaultZone_returnsDateWithStandardYearLength() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedDate today = chronology.dateNow();
        assertEquals(365, today.lengthOfYear());
    }

    @Test(timeout = 4000)
    public void dateNow_withUtcZoneId_returnsDateWithStandardYearLength() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        ZoneOffset utc = ZoneOffset.UTC;
        InternationalFixedDate today = chronology.dateNow((ZoneId) utc);
        assertEquals(365, today.lengthOfYear());
    }

    @Test(timeout = 4000)
    public void dateNow_withMockClock_returnsDateWithStandardYearLength() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Clock mockClock = MockClock.systemUTC();
        InternationalFixedDate today = chronology.dateNow(mockClock);
        assertEquals(365, today.lengthOfYear());
    }

    // -----------------------------------------------------------------------
    // zonedDateTime
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void zonedDateTime_fromInstantAndZone_returnsNonNull() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        Instant instant = MockInstant.ofEpochSecond(3L, 3L);
        ZoneOffset utc = ZoneOffset.UTC;
        ChronoZonedDateTime<InternationalFixedDate> zdt = chronology.zonedDateTime(instant, (ZoneId) utc);
        assertNotNull(zdt);
    }

    @Test(timeout = 4000)
    public void zonedDateTime_fromDateWithoutZoneInfo_throwsDateTimeException() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;
        InternationalFixedDate date = chronology.dateYearDay(7, 7);
        // A plain date has no zone attached; converting to ChronoZonedDateTime must fail
        try {
            chronology.zonedDateTime((TemporalAccessor) date);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.chrono.Chronology", e);
        }
    }
}
