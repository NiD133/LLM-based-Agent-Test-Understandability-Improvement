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
import static org.junit.jupiter.api.Assertions.assertNull;
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
import java.time.chrono.HijrahEra;
import java.time.chrono.IsoEra;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.testing.EqualsTester;

/**
 * Tests for {@link Symmetry010Chronology} that verify chronology registration and
 * core identity properties (ID, calendar type, singleton instance).
 *
 * <p>This class also serves as the data-provider source for the broader
 * Symmetry010 test suite; the {@code data_*()} static methods are referenced
 * via {@code @MethodSource} from sibling test classes.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_chronology {

    // -----------------------------------------------------------------------
    // Data providers used by sibling test classes via @MethodSource
    // -----------------------------------------------------------------------

    /**
     * Pairs of (Symmetry010Date, equivalent ISO LocalDate) for historical dates,
     * used to verify round-trip conversion between the two calendar systems.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry010Date.of(1, 1, 1),       LocalDate.of(1, 1, 1) },
            { Symmetry010Date.of(272, 2, 28),     LocalDate.of(272, 2, 27) },
            { Symmetry010Date.of(272, 2, 27),     LocalDate.of(272, 2, 26) },
            // Charlemagne, Frankish king (d. 814)
            { Symmetry010Date.of(742, 3, 27),     LocalDate.of(742, 4, 2) },
            { Symmetry010Date.of(742, 4, 2),      LocalDate.of(742, 4, 7) },
            // Norman Conquest: Battle of Hastings
            { Symmetry010Date.of(1066, 10, 14),   LocalDate.of(1066, 10, 14) },
            // Francesco Petrarca - Petrarch, Italian scholar and poet (d. 1374)
            { Symmetry010Date.of(1304, 7, 21),    LocalDate.of(1304, 7, 20) },
            { Symmetry010Date.of(1304, 7, 20),    LocalDate.of(1304, 7, 19) },
            // Charles the Bold, French son of Isabella of Portugal (d. 1477)
            { Symmetry010Date.of(1433, 11, 12),   LocalDate.of(1433, 11, 10) },
            { Symmetry010Date.of(1433, 11, 10),   LocalDate.of(1433, 11, 8) },
            // Leonardo da Vinci, Italian painter, sculptor, and architect (d. 1519)
            { Symmetry010Date.of(1452, 4, 11),    LocalDate.of(1452, 4, 15) },
            { Symmetry010Date.of(1452, 4, 15),    LocalDate.of(1452, 4, 19) },
            // Christopher Columbus's expedition makes landfall in the Caribbean
            { Symmetry010Date.of(1492, 10, 10),   LocalDate.of(1492, 10, 12) },
            { Symmetry010Date.of(1492, 10, 12),   LocalDate.of(1492, 10, 14) },
            // Galileo Galilei, Italian astronomer and physicist (d. 1642)
            { Symmetry010Date.of(1564, 2, 18),    LocalDate.of(1564, 2, 15) },
            { Symmetry010Date.of(1564, 2, 15),    LocalDate.of(1564, 2, 12) },
            // William Shakespeare baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry010Date.of(1564, 4, 28),    LocalDate.of(1564, 4, 26) },
            { Symmetry010Date.of(1564, 4, 26),    LocalDate.of(1564, 4, 24) },
            // Sir Isaac Newton, English physicist and mathematician (d. 1727)
            { Symmetry010Date.of(1643, 1, 7),     LocalDate.of(1643, 1, 4) },
            { Symmetry010Date.of(1643, 1, 4),     LocalDate.of(1643, 1, 1) },
            // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
            { Symmetry010Date.of(1707, 4, 12),    LocalDate.of(1707, 4, 15) },
            { Symmetry010Date.of(1707, 4, 15),    LocalDate.of(1707, 4, 18) },
            // French Revolution: Citizens of Paris storm the Bastille
            { Symmetry010Date.of(1789, 7, 16),    LocalDate.of(1789, 7, 14) },
            { Symmetry010Date.of(1789, 7, 14),    LocalDate.of(1789, 7, 12) },
            // Albert Einstein, German theoretical physicist (d. 1955)
            { Symmetry010Date.of(1879, 3, 14),    LocalDate.of(1879, 3, 14) },
            // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
            { Symmetry010Date.of(1941, 9, 11),    LocalDate.of(1941, 9, 9) },
            { Symmetry010Date.of(1941, 9, 9),     LocalDate.of(1941, 9, 7) },
            // Unix time begins at 00:00:00 UTC/GMT
            { Symmetry010Date.of(1970, 1, 4),     LocalDate.of(1970, 1, 1) },
            { Symmetry010Date.of(1970, 1, 1),     LocalDate.of(1969, 12, 29) },
            // Start of the 21st century / 3rd millennium
            { Symmetry010Date.of(1999, 12, 29),   LocalDate.of(2000, 1, 1) },
            { Symmetry010Date.of(2000, 1, 1),     LocalDate.of(2000, 1, 3) },
        };
    }

    /**
     * Invalid (year, month, day) triples that should each cause a
     * {@link DateTimeException} when passed to {@code Symmetry010Date.of()}.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Negative/zero month or invalid month number
            { -1,   13, 28 }, { -1,   13, 29 },
            { 2000, -2,  1 }, { 2000,  13,  1 }, { 2000, 15,  1 },
            // Negative or zero day
            { 2000,  1, -1 }, { 2000,  1,  0 }, { 2000,  0,  1 },
            { 2000, -1,  0 }, { 2000, -1,  1 },
            // Day exceeds month length for every month (non-leap year 2000)
            { 2000,  1, 31 }, { 2000,  2, 32 }, { 2000,  3, 31 },
            { 2000,  4, 31 }, { 2000,  5, 32 }, { 2000,  6, 31 },
            { 2000,  7, 31 }, { 2000,  8, 32 }, { 2000,  9, 31 },
            { 2000, 10, 31 }, { 2000, 11, 32 }, { 2000, 12, 31 },
            // Leap-week day in a non-leap year
            { 2004, 12, 38 },
        };
    }

    /**
     * Years that are NOT leap years in Symmetry010 (the leap-week formula
     * excludes these), used to verify that no extra week is appended.
     */
    public static Object[][] data_badLeapDates() {
        return new Object[][] {
            { 1 }, { 100 }, { 200 }, { 2000 },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (year, month, day, expectedLength) tuples verifying the number of days
     * in each month, including December in a leap year (37 days).
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Standard months in non-leap year 2000: 30/31 alternating per quarter
            { 2000,  1, 28, 30 }, { 2000,  2, 28, 31 }, { 2000,  3, 28, 30 },
            { 2000,  4, 28, 30 }, { 2000,  5, 28, 31 }, { 2000,  6, 28, 30 },
            { 2000,  7, 28, 30 }, { 2000,  8, 28, 31 }, { 2000,  9, 28, 30 },
            { 2000, 10, 28, 30 }, { 2000, 11, 28, 31 }, { 2000, 12, 28, 30 },
            // December in leap year 2004 gains the leap week (30 + 7 = 37)
            { 2004, 12, 20, 37 },
        };
    }

    /**
     * Era types that are incompatible with {@link Symmetry010Chronology#prolepticYear},
     * which only accepts {@link IsoEra}. Each entry should cause a
     * {@link ClassCastException}.
     */
    public static Object[][] data_prolepticYear_badEra() {
        return new Era[][] {
            { AccountingEra.BCE }, { AccountingEra.CE },
            { CopticEra.BEFORE_AM }, { CopticEra.AM },
            { DiscordianEra.YOLD },
            { EthiopicEra.BEFORE_INCARNATION }, { EthiopicEra.INCARNATION },
            { HijrahEra.AH },
            { InternationalFixedEra.CE },
            { JapaneseEra.MEIJI }, { JapaneseEra.TAISHO }, { JapaneseEra.SHOWA }, { JapaneseEra.HEISEI },
            { JulianEra.BC }, { JulianEra.AD },
            { MinguoEra.BEFORE_ROC }, { MinguoEra.ROC },
            { PaxEra.BCE }, { PaxEra.CE },
            { ThaiBuddhistEra.BEFORE_BE }, { ThaiBuddhistEra.BE },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (year, month, day, field, expectedRange) tuples verifying that
     * {@code Symmetry010Date.range(field)} returns the correct {@link ValueRange}
     * for each combination, including leap-year December and week-based fields.
     *
     * <p>Leap Day and Year Day are members of months (no orphan days outside a month).
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: 30 for months 1/3/4/6/7/9/10/12, 31 for 2/5/8/11
            { 2012,  1, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012,  2, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012,  3, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012,  4, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012,  5, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012,  7, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012,  8, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012,  9, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            // December in a leap year has 37 days
            { 2015, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 37) },
            // DAY_OF_WEEK is always 1-7
            { 2012,  1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012,  6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            // DAY_OF_YEAR: 364 in normal years, 371 in leap years
            { 2012,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 364) },
            { 2015,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 371) },
            // MONTH_OF_YEAR is always 1-12
            { 2012,  1, 23, MONTH_OF_YEAR, ValueRange.of(1, 12) },
            // ALIGNED_DAY_OF_WEEK_IN_MONTH is always 1-7
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            // ALIGNED_WEEK_OF_MONTH: 1-4 for 30-day months, 1-5 for 31-day leap December
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012,  2, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2015, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) },
            // ALIGNED_DAY_OF_WEEK_IN_YEAR is always 1-7
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            // ALIGNED_WEEK_OF_YEAR: 1-52 in normal years, 1-53 in leap years
            { 2012,  1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012,  6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2015, 12, 30, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 53) },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (year, month, day, field, expectedValue) tuples verifying the long value
     * returned by {@code Symmetry010Date.getLong(field)}.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // Standard mid-year date: 2014-05-26
            { 2014, 5, 26, DAY_OF_WEEK,                  2 },
            { 2014, 5, 26, DAY_OF_MONTH,                 26 },
            { 2014, 5, 26, DAY_OF_YEAR,                  30 + 31 + 30 + 30 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  7 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         4 + 5 + 4 + 4 + 4 },
            { 2014, 5, 26, MONTH_OF_YEAR,                5 },
            { 2014, 5, 26, PROLEPTIC_MONTH,              2014 * 12 + 5 - 1 },
            { 2014, 5, 26, YEAR,                         2014 },
            { 2014, 5, 26, ERA,                          1 },
            { 1,    5,  8, ERA,                          1 },
            // End-of-third-quarter date in a non-leap year: 2012-09-26
            { 2012, 9, 26, DAY_OF_WEEK,                  1 },
            { 2012, 9, 26, DAY_OF_YEAR,                  3 * (4 + 5 + 4) * 7 - 4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_MONTH,        4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_YEAR,         3 * (4 + 5 + 4) },
            // Last day of the leap week in a leap year: 2015-12-37
            { 2015, 12, 37, DAY_OF_WEEK,                  5 },
            { 2015, 12, 37, DAY_OF_MONTH,                 37 },
            { 2015, 12, 37, DAY_OF_YEAR,                  4 * (4 + 5 + 4) * 7 + 7 },
            { 2015, 12, 37, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2 },
            { 2015, 12, 37, ALIGNED_WEEK_OF_MONTH,        6 },
            { 2015, 12, 37, ALIGNED_DAY_OF_WEEK_IN_YEAR,  7 },
            { 2015, 12, 37, ALIGNED_WEEK_OF_YEAR,         53 },
            { 2015, 12, 37, MONTH_OF_YEAR,                12 },
            { 2015, 12, 37, PROLEPTIC_MONTH,              2016 * 12 - 1 },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (year, month, day, field, newValue, expectedYear, expectedMonth, expectedDay)
     * tuples verifying that {@code Symmetry010Date.with(field, value)} produces
     * the correct adjusted date.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            { 2014, 5, 26, DAY_OF_WEEK,                  1, 2014, 5,  20 },
            { 2014, 5, 26, DAY_OF_WEEK,                  5, 2014, 5,  24 },
            { 2014, 5, 26, DAY_OF_MONTH,                28, 2014, 5,  28 },
            { 2014, 5, 26, DAY_OF_MONTH,                26, 2014, 5,  26 },
            { 2014, 5, 26, DAY_OF_YEAR,                364, 2014, 12, 30 },
            { 2014, 5, 26, DAY_OF_YEAR,                138, 2014, 5,  17 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5,  24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5,  26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        1, 2014, 5,   5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        4, 2014, 5,  26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2, 2014, 5,  21 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5, 2014, 5,  24 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         23, 2014, 6,   9 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         20, 2014, 5,  19 },
            { 2014, 5, 26, MONTH_OF_YEAR,                 4, 2014, 4,  26 },
            { 2014, 5, 26, MONTH_OF_YEAR,                 5, 2014, 5,  26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3,  26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5,  26 },
            { 2014, 5, 26, YEAR,                       2012, 2012, 5,  26 },
            { 2014, 5, 26, YEAR,                       2014, 2014, 5,  26 },
            { 2014, 5, 26, YEAR_OF_ERA,                2012, 2012, 5,  26 },
            { 2014, 5, 26, YEAR_OF_ERA,                2014, 2014, 5,  26 },
            { 2014, 5, 26, ERA,                           1, 2014, 5,  26 },
            // Leap-week month: aligned-day-of-week-in-month on 2015-12-22
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2015, 12, 23 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2015, 12, 24 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2015, 12, 25 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2015, 12, 26 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6, 2015, 12, 27 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7, 2015, 12, 28 },
            // Aligned-day-of-week-in-year on 2015-12-22
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  1, 2015, 12, 17 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2, 2015, 12, 18 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3, 2015, 12, 19 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  4, 2015, 12, 20 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5, 2015, 12, 21 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  6, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR,  7, 2015, 12, 23 },
            // Zero-valued week fields on a date in the leap week (no adjustment)
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH,  0, 2015, 12, 29 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH,  3, 2015, 12, 15 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR,   0, 2015, 12, 29 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR,   3, 2015,  1, 20 },
            { 2015, 12, 29, DAY_OF_WEEK,             0, 2015, 12, 29 },
            // Day-of-week adjustments across a leap-week boundary: 2015-12-28
            { 2015, 12, 28, DAY_OF_WEEK,             1, 2015, 12, 24 },
            { 2015, 12, 28, DAY_OF_WEEK,             2, 2015, 12, 25 },
            { 2015, 12, 28, DAY_OF_WEEK,             3, 2015, 12, 26 },
            { 2015, 12, 28, DAY_OF_WEEK,             4, 2015, 12, 27 },
            { 2015, 12, 28, DAY_OF_WEEK,             5, 2015, 12, 28 },
            { 2015, 12, 28, DAY_OF_WEEK,             6, 2015, 12, 29 },
            { 2015, 12, 28, DAY_OF_WEEK,             7, 2015, 12, 30 },
            // Day/month adjustments on a date in the leap week: 2015-12-29
            { 2015, 12, 29, DAY_OF_MONTH,   1, 2015, 12,  1 },
            { 2015, 12, 29, DAY_OF_MONTH,   3, 2015, 12,  3 },
            { 2015, 12, 29, MONTH_OF_YEAR,  1, 2015,  1, 29 },
            { 2015, 12, 29, MONTH_OF_YEAR, 12, 2015, 12, 29 },
            { 2015, 12, 29, MONTH_OF_YEAR,  2, 2015,  2, 29 },
            // Changing year when the day is in the leap week: clamp to month end if needed
            { 2015, 12, 37, YEAR, 2004, 2004, 12, 37 },
            { 2015, 12, 37, YEAR, 2013, 2013, 12, 30 },
            // Misc coverage: end-of-short-month and month change
            { 2014,  3, 28, DAY_OF_MONTH,   1, 2014,  3,  1 },
            { 2014,  1, 28, DAY_OF_MONTH,   1, 2014,  1,  1 },
            { 2014,  3, 28, MONTH_OF_YEAR,  1, 2014,  1, 28 },
            // Setting day-of-year to the last day of a leap/non-leap year
            { 2015,  3, 28, DAY_OF_YEAR, 371, 2015, 12, 37 },
            { 2012,  3, 28, DAY_OF_YEAR, 364, 2012, 12, 30 },
        };
    }

    /**
     * (year, month, day, field, invalidValue) tuples that should each cause a
     * {@link DateTimeException} when passed to {@code Symmetry010Date.with(field, value)}.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH,  8 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR,  -1 },
            { 2013, 1, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR,   8 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_MONTH,         -1 },
            { 2013, 2, 1, ALIGNED_WEEK_OF_MONTH,          6 },
            { 2013, 1, 1, ALIGNED_WEEK_OF_YEAR,          -1 },
            { 2015, 1, 1, ALIGNED_WEEK_OF_YEAR,          54 },
            { 2013, 1, 1, DAY_OF_WEEK,  -1 }, { 2013, 1, 1, DAY_OF_WEEK,   8 },
            { 2013, 1, 1, DAY_OF_MONTH, -1 }, { 2013, 1, 1, DAY_OF_MONTH, 31 },
            { 2013, 6, 1, DAY_OF_MONTH, 31 }, { 2013, 12, 1, DAY_OF_MONTH, 31 },
            { 2015, 12, 1, DAY_OF_MONTH, 38 },
            { 2013, 1, 1, DAY_OF_YEAR,  -1 }, { 2013, 1, 1, DAY_OF_YEAR, 365 },
            { 2015, 1, 1, DAY_OF_YEAR, 372 },
            { 2013, 1, 1, MONTH_OF_YEAR,  -1 }, { 2013, 1, 1, MONTH_OF_YEAR, 14 },
            { 2013, 1, 1, MONTH_OF_YEAR,  -2 }, { 2015, 1, 1, MONTH_OF_YEAR, 14 },
            { 2013, 1, 1, EPOCH_DAY, -365_961_481 }, { 2013, 1, 1, EPOCH_DAY, 364_523_156 },
            { 2013, 1, 1, YEAR, -1_000_001 },  { 2013, 1, 1, YEAR, 1_000_001 },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (year, month, day, expectedYear, expectedMonth, expectedDay) tuples verifying
     * that the {@link TemporalAdjusters#lastDayOfMonth()} adjuster returns the
     * correct last day, including the 37-day December in leap year 2009.
     */
    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
            { 2012,  1, 23, 2012,  1, 30 }, { 2012,  2, 23, 2012,  2, 31 },
            { 2012,  3, 23, 2012,  3, 30 }, { 2012,  4, 23, 2012,  4, 30 },
            { 2012,  5, 23, 2012,  5, 31 }, { 2012,  6, 23, 2012,  6, 30 },
            { 2012,  7, 23, 2012,  7, 30 }, { 2012,  8, 23, 2012,  8, 31 },
            { 2012,  9, 23, 2012,  9, 30 }, { 2012, 10, 23, 2012, 10, 30 },
            { 2012, 11, 23, 2012, 11, 31 }, { 2012, 12, 23, 2012, 12, 30 },
            // Leap year: December has 37 days (30 standard + 7 leap week)
            { 2009, 12, 23, 2009, 12, 37 },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (year, month, day, amount, unit, expectedYear, expectedMonth, expectedDay)
     * tuples verifying {@code Symmetry010Date.plus(amount, unit)} in non-leap years.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // Adding/subtracting DAYS
            { 2014, 5, 26,  0, DAYS, 2014, 5, 26 },
            { 2014, 5, 26,  8, DAYS, 2014, 6,  3 },
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },
            // Adding/subtracting WEEKS
            { 2014, 5, 26,  0, WEEKS, 2014, 5, 26 },
            { 2014, 5, 26,  3, WEEKS, 2014, 6, 16 },
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 21 },
            // Adding/subtracting MONTHS
            { 2014, 5, 26,  0, MONTHS, 2014,  5, 26 },
            { 2014, 5, 26,  3, MONTHS, 2014,  8, 26 },
            { 2014, 5, 26, -5, MONTHS, 2013, 12, 26 },
            // Adding/subtracting YEARS
            { 2014, 5, 26,  0, YEARS, 2014, 5, 26 },
            { 2014, 5, 26,  3, YEARS, 2017, 5, 26 },
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },
            // Adding/subtracting DECADES
            { 2014, 5, 26,  0, DECADES, 2014, 5, 26 },
            { 2014, 5, 26,  3, DECADES, 2044, 5, 26 },
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },
            // Adding/subtracting CENTURIES
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26,  3, CENTURIES, 2314, 5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },
            // Adding/subtracting MILLENNIA
            { 2014, 5, 26,  0, MILLENNIA,       2014, 5, 26 },
            { 2014, 5, 26,  3, MILLENNIA,       5014, 5, 26 },
            { 2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26 },
            // Week arithmetic crossing year and quarter boundaries
            { 2014, 12, 26,  3, WEEKS, 2015,  1, 17 },
            { 2014,  1, 26, -5, WEEKS, 2013, 12, 21 },
            { 2012,  6, 26,  3, WEEKS, 2012,  7, 17 },
            { 2012,  7, 26, -5, WEEKS, 2012,  6, 21 },
            { 2012,  6, 21, 52 + 1,     WEEKS, 2013,  6, 28 },
            { 2013,  6, 21, 6 * 52 + 1, WEEKS, 2019,  6, 21 },
        };
    }

    /**
     * (year, month, day, amount, unit, expectedYear, expectedMonth, expectedDay)
     * tuples verifying {@code Symmetry010Date.plus(amount, unit)} when the
     * starting date or result crosses the leap week.
     */
    public static Object[][] data_plus_leapWeek() {
        return new Object[][] {
            // Adding/subtracting DAYS around the leap week
            { 2015, 12, 28,  0, DAYS, 2015, 12, 28 },
            { 2015, 12, 28,  8, DAYS, 2015, 12, 36 },
            { 2015, 12, 28, -3, DAYS, 2015, 12, 25 },
            // Adding/subtracting WEEKS across the leap week
            { 2015, 12, 28,  0, WEEKS, 2015, 12, 28 },
            { 2015, 12, 28,  3, WEEKS, 2016,  1, 12 },
            { 2015, 12, 28, -5, WEEKS, 2015, 11, 24 },
            { 2015, 12, 28, 52, WEEKS, 2016, 12, 21 },
            // Adding/subtracting MONTHS
            { 2015, 12, 28,  0, MONTHS, 2015, 12, 28 },
            { 2015, 12, 28,  3, MONTHS, 2016,  3, 28 },
            { 2015, 12, 28, -5, MONTHS, 2015,  7, 28 },
            { 2015, 12, 28, 12, MONTHS, 2016, 12, 28 },
            // Adding/subtracting YEARS
            { 2015, 12, 28,  0, YEARS, 2015, 12, 28 },
            { 2015, 12, 28,  3, YEARS, 2018, 12, 28 },
            { 2015, 12, 28, -5, YEARS, 2010, 12, 28 },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (startYear, startMonth, startDay, endYear, endMonth, endDay, unit, expectedAmount)
     * tuples verifying {@code Symmetry010Date.until(end, unit)}.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            { 2014, 5, 26, 2014, 5, 26, DAYS,      0 },
            { 2014, 5, 26, 2014, 6,  4, DAYS,      9 },
            { 2014, 5, 26, 2014, 5, 20, DAYS,     -6 },
            { 2014, 5, 26, 2014, 5, 26, WEEKS,     0 },
            { 2014, 5, 26, 2014, 6,  1, WEEKS,     1 },
            { 2014, 5, 26, 2014, 6,  5, WEEKS,     1 },
            { 2014, 5, 26, 2014, 5, 26, MONTHS,    0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS,    0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS,    1 },
            { 2014, 5, 26, 2014, 5, 26, YEARS,     0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS,     0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS,     1 },
            { 2014, 5, 26, 2014, 5, 26, DECADES,   0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES,   0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES,   1 },
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },
            // ERAS always returns 0 (both dates are in CE)
            { 2014, 5, 26, 3014, 5, 26, ERAS,      0 },
        };
    }

    /**
     * (startYear, startMonth, startDay, endYear, endMonth, endDay,
     *  expectedYears, expectedMonths, expectedDays)
     * tuples verifying {@code Symmetry010Date.until(end)} as a {@link ChronoPeriod}.
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            { 2014, 5, 26, 2014, 5, 26, 0,  0,  0 },
            { 2014, 5, 26, 2014, 6,  4, 0,  0,  9 },
            { 2014, 5, 26, 2014, 5, 20, 0,  0, -6 },
            { 2014, 5, 26, 2014, 6,  5, 0,  0, 10 },
            { 2014, 5, 26, 2014, 6, 25, 0,  0, 30 },
            { 2014, 5, 26, 2014, 6, 26, 0,  1,  0 },
            { 2014, 5, 26, 2015, 5, 25, 0, 11, 29 },
            { 2014, 5, 26, 2015, 5, 26, 1,  0,  0 },
            { 2014, 5, 26, 2024, 5, 25, 9, 11, 29 },
        };
    }

    // -----------------------------------------------------------------------

    /**
     * (date, expectedString) pairs verifying the output of {@code Symmetry010Date.toString()}.
     * The format is {@code "Sym010 CE yyyy/MM/dd"}.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            { Symmetry010Date.of(1,    1,  1), "Sym010 CE 1/01/01"    },
            { Symmetry010Date.of(1970, 2, 31), "Sym010 CE 1970/02/31" },
            { Symmetry010Date.of(2000, 8, 31), "Sym010 CE 2000/08/31" },
            { Symmetry010Date.of(2009, 12, 37), "Sym010 CE 2009/12/37" },
        };
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that {@link Symmetry010Chronology} is registered under the ID
     * {@code "Sym010"} and correctly reports its identity properties:
     * <ul>
     *   <li>The chronology can be looked up via {@link Chronology#of(String)} using
     *       the ID {@code "Sym010"}.</li>
     *   <li>The resolved instance is the singleton {@link Symmetry010Chronology#INSTANCE}.</li>
     *   <li>{@link Chronology#getId()} returns {@code "Sym010"}.</li>
     *   <li>{@link Chronology#getCalendarType()} returns {@code null} because
     *       the Unicode CLDR does not define a type identifier for this calendar.</li>
     * </ul>
     */
    @Test
    public void test_chronology() {
        Chronology chrono = Chronology.of("Sym010");
        assertNotNull(chrono);
        assertEquals(Symmetry010Chronology.INSTANCE, chrono);
        assertEquals("Sym010", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
