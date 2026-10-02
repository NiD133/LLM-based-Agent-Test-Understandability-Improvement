package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;
import org.threeten.extra.chrono.Symmetry010Chronology;
import org.threeten.extra.chrono.Symmetry010Date;
import org.threeten.extra.chrono.Symmetry454Date;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest extends Symmetry010Chronology_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // dateYearDay – boundary validation
    // -----------------------------------------------------------------------

    /** Day-of-year value 0 is below the valid minimum (1), so a DateTimeException must be thrown. */
    @Test(timeout = 4000)
    public void testDateYearDay_withDayOfYearZero_throwsDateTimeException() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        IsoEra era = Symmetry454Date.now().getEra();
        try {
            chronology.dateYearDay((Era) era, 4, 0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid value for DayOfYear (valid values 1 - 364/371): 0
            //
            verifyException("java.time.temporal.ValueRange", e);
        }
    }

    // -----------------------------------------------------------------------
    // range() – returns non-null ValueRange for every supported ChronoField
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void testRange_forYearField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forYearOfEraField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.YEAR_OF_ERA);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forProlepticMonthField_returnsValidRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ValueRange range = chronology.range(ChronoField.PROLEPTIC_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forMonthOfYearField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.MONTH_OF_YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forEraField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.ERA);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forEpochDayField_returnsValidRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ValueRange range = chronology.range(ChronoField.EPOCH_DAY);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forDayOfYearField_returnsValidRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ValueRange range = chronology.range(ChronoField.DAY_OF_YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forDayOfMonthField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.DAY_OF_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forAlignedWeekOfYearField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.ALIGNED_WEEK_OF_YEAR);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forAlignedWeekOfMonthField_returnsValidRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ValueRange range = chronology.range(ChronoField.ALIGNED_WEEK_OF_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forDayOfWeekField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.DAY_OF_WEEK);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forAlignedDayOfWeekInMonthField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forOffsetSecondsField_returnsValidRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ValueRange range = chronology.range(ChronoField.OFFSET_SECONDS);
        assertNotNull(range);
    }

    @Test(timeout = 4000)
    public void testRange_forAlignedDayOfWeekInYearField_returnsValidRange() throws Throwable {
        ValueRange range = Symmetry010Chronology.INSTANCE.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
        assertNotNull(range);
    }

    // -----------------------------------------------------------------------
    // date creation – era and epoch-day variants
    // -----------------------------------------------------------------------

    /** Epoch day 3 falls in the Common Era (CE). */
    @Test(timeout = 4000)
    public void testOfEpochDay_epochDayThree_returnsCommonEraDate() throws Throwable {
        Symmetry010Date date = Symmetry010Date.ofEpochDay(3);
        assertEquals(IsoEra.CE, date.getEra());
    }

    /** Passing a null Era to date() must throw ClassCastException with a descriptive message. */
    @Test(timeout = 4000)
    public void testDate_withNullEra_throwsClassCastException() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        try {
            chronology.INSTANCE.date((Era) null, (-1134), 2336, (-1134));
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            //
            // Invalid era: null
            //
            verifyException("org.threeten.extra.chrono.Symmetry010Chronology", e);
        }
    }

    /** Proleptic year 9, month 10, day 10 should fall within the Common Era. */
    @Test(timeout = 4000)
    public void testDate_prolepticYearMonthDay_returnsCommonEraDate() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Symmetry010Date date = chronology.INSTANCE.date(9, 10, 10);
        assertEquals(IsoEra.CE, date.getEra());
    }

    /** Epoch day 702 falls in the Common Era. */
    @Test(timeout = 4000)
    public void testDateEpochDay_epochDay702_returnsCommonEraDate() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        Symmetry010Date date = chronology.INSTANCE.dateEpochDay(702L);
        assertEquals(IsoEra.CE, date.getEra());
    }

    // -----------------------------------------------------------------------
    // display name
    // -----------------------------------------------------------------------

    /** The calendar's display name is always "Sym010" regardless of locale. */
    @Test(timeout = 4000)
    public void testGetDisplayName_fullStyleTaiwanLocale_returnsSym010() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        String displayName = chronology.getDisplayName(TextStyle.FULL, Locale.TAIWAN);
        assertEquals("Sym010", displayName);
    }

    // -----------------------------------------------------------------------
    // eraOf – validation
    // -----------------------------------------------------------------------

    /** Era value -517 is out of the valid range [0, 1], so a DateTimeException must be thrown. */
    @Test(timeout = 4000)
    public void testEraOf_withInvalidEraValue_throwsDateTimeException() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        try {
            chronology.eraOf((-517));
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid era: -517
            //
            verifyException("java.time.chrono.IsoEra", e);
        }
    }

    // -----------------------------------------------------------------------
    // eras()
    // -----------------------------------------------------------------------

    /** Symmetry010 shares the two ISO eras (BCE and CE). */
    @Test(timeout = 4000)
    public void testEras_returnsTwoEras() throws Throwable {
        List<Era> eras = Symmetry010Chronology.INSTANCE.eras();
        assertEquals(2, eras.size());
    }

    // -----------------------------------------------------------------------
    // dateNow – system clock, zone, and explicit clock variants
    // -----------------------------------------------------------------------

    /** dateNow() using the system default zone should return a date in the Common Era. */
    @Test(timeout = 4000)
    public void testDateNow_defaultClock_returnsCommonEraDate() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        Symmetry010Date date = chronology.dateNow();
        assertEquals(IsoEra.CE, date.getEra());
    }

    /** dateNow(ZoneId) using the system default zone should return a date in the Common Era. */
    @Test(timeout = 4000)
    public void testDateNow_withZoneId_returnsCommonEraDate() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ZoneId zone = ZoneId.systemDefault();
        Symmetry010Date date = chronology.dateNow(zone);
        assertEquals(IsoEra.CE, date.getEra());
    }

    /** dateNow(Clock) using a mocked system clock should return a date in the Common Era. */
    @Test(timeout = 4000)
    public void testDateNow_withMockedClock_returnsCommonEraDate() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Clock clock = MockClock.systemDefaultZone();
        Symmetry010Date date = chronology.dateNow(clock);
        assertEquals(IsoEra.CE, date.getEra());
    }

    // -----------------------------------------------------------------------
    // zonedDateTime
    // -----------------------------------------------------------------------

    /** Converting a mocked OffsetDateTime to a Symmetry010 zoned date-time should succeed. */
    @Test(timeout = 4000)
    public void testZonedDateTime_fromOffsetDateTime_returnsNonNull() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        OffsetDateTime offsetDateTime = MockOffsetDateTime.now();
        ChronoZonedDateTime<Symmetry010Date> zonedDateTime = chronology.zonedDateTime((TemporalAccessor) offsetDateTime);
        assertNotNull(zonedDateTime);
    }
}
