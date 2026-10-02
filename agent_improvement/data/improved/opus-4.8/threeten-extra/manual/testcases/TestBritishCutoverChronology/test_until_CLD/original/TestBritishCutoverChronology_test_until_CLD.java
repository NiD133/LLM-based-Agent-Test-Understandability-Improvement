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

public class TestBritishCutoverChronology_test_until_CLD {

    //-----------------------------------------------------------------------
    public static Object[][] data_samples() {
        return new Object[][] { { BritishCutoverDate.of(1, 1, 1), LocalDate.of(0, 12, 30) }, { BritishCutoverDate.of(1, 1, 2), LocalDate.of(0, 12, 31) }, { BritishCutoverDate.of(1, 1, 3), LocalDate.of(1, 1, 1) }, { BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26) }, { BritishCutoverDate.of(1, 3, 1), LocalDate.of(1, 2, 27) }, { BritishCutoverDate.of(1, 3, 2), LocalDate.of(1, 2, 28) }, { BritishCutoverDate.of(1, 3, 3), LocalDate.of(1, 3, 1) }, { BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26) }, { BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27) }, { BritishCutoverDate.of(4, 3, 1), LocalDate.of(4, 2, 28) }, { BritishCutoverDate.of(4, 3, 2), LocalDate.of(4, 2, 29) }, { BritishCutoverDate.of(4, 3, 3), LocalDate.of(4, 3, 1) }, { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) }, { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) }, { BritishCutoverDate.of(100, 3, 1), LocalDate.of(100, 2, 28) }, { BritishCutoverDate.of(100, 3, 2), LocalDate.of(100, 3, 1) }, { BritishCutoverDate.of(100, 3, 3), LocalDate.of(100, 3, 2) }, { BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29) }, { BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28) }, { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) }, { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) }, { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) }, { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) }, { BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12) }, { BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12) }, { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) }, // leniently accept invalid
        { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) }, // leniently accept invalid
        { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) }, { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) }, { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) }, { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) }, { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) } };
    }

    public static Object[][] data_badDates() {
        return new Object[][] { { 1900, 0, 0 }, { 1900, -1, 1 }, { 1900, 0, 1 }, { 1900, 13, 1 }, { 1900, 14, 1 }, { 1900, 1, -1 }, { 1900, 1, 0 }, { 1900, 1, 32 }, { 1900, 2, -1 }, { 1900, 2, 0 }, { 1900, 2, 30 }, { 1900, 2, 31 }, { 1900, 2, 32 }, { 1899, 2, -1 }, { 1899, 2, 0 }, { 1899, 2, 29 }, { 1899, 2, 30 }, { 1899, 2, 31 }, { 1899, 2, 32 }, { 1900, 12, -1 }, { 1900, 12, 0 }, { 1900, 12, 32 }, { 1900, 3, 32 }, { 1900, 4, 31 }, { 1900, 5, 32 }, { 1900, 6, 31 }, { 1900, 7, 32 }, { 1900, 8, 32 }, { 1900, 9, 31 }, { 1900, 10, 32 }, { 1900, 11, 31 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] { { 1700, 1, 31 }, { 1700, 2, 29 }, { 1700, 3, 31 }, { 1700, 4, 30 }, { 1700, 5, 31 }, { 1700, 6, 30 }, { 1700, 7, 31 }, { 1700, 8, 31 }, { 1700, 9, 30 }, { 1700, 10, 31 }, { 1700, 11, 30 }, { 1700, 12, 31 }, { 1751, 1, 31 }, { 1751, 2, 28 }, { 1751, 3, 31 }, { 1751, 4, 30 }, { 1751, 5, 31 }, { 1751, 6, 30 }, { 1751, 7, 31 }, { 1751, 8, 31 }, { 1751, 9, 30 }, { 1751, 10, 31 }, { 1751, 11, 30 }, { 1751, 12, 31 }, { 1752, 1, 31 }, { 1752, 2, 29 }, { 1752, 3, 31 }, { 1752, 4, 30 }, { 1752, 5, 31 }, { 1752, 6, 30 }, { 1752, 7, 31 }, { 1752, 8, 31 }, { 1752, 9, 19 }, { 1752, 10, 31 }, { 1752, 11, 30 }, { 1752, 12, 31 }, { 1753, 1, 31 }, { 1753, 3, 31 }, { 1753, 2, 28 }, { 1753, 4, 30 }, { 1753, 5, 31 }, { 1753, 6, 30 }, { 1753, 7, 31 }, { 1753, 8, 31 }, { 1753, 9, 30 }, { 1753, 10, 31 }, { 1753, 11, 30 }, { 1753, 12, 31 }, { 1500, 2, 29 }, { 1600, 2, 29 }, { 1700, 2, 29 }, { 1800, 2, 28 }, { 1900, 2, 28 }, { 1901, 2, 28 }, { 1902, 2, 28 }, { 1903, 2, 28 }, { 1904, 2, 29 }, { 2000, 2, 29 }, { 2100, 2, 28 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_lengthOfYear() {
        return new Object[][] { { -101, 365 }, { -100, 366 }, { -99, 365 }, { -1, 365 }, { 0, 366 }, { 100, 366 }, { 1600, 366 }, { 1700, 366 }, { 1751, 365 }, { 1748, 366 }, { 1749, 365 }, { 1750, 365 }, { 1751, 365 }, { 1752, 355 }, { 1753, 365 }, { 1500, 366 }, { 1600, 366 }, { 1700, 366 }, { 1800, 365 }, { 1900, 365 }, { 1901, 365 }, { 1902, 365 }, { 1903, 365 }, { 1904, 366 }, { 2000, 366 }, { 2100, 365 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_ranges() {
        return new Object[][] { { 1700, 1, 23, DAY_OF_MONTH, 1, 31 }, { 1700, 2, 23, DAY_OF_MONTH, 1, 29 }, { 1700, 3, 23, DAY_OF_MONTH, 1, 31 }, { 1700, 4, 23, DAY_OF_MONTH, 1, 30 }, { 1700, 5, 23, DAY_OF_MONTH, 1, 31 }, { 1700, 6, 23, DAY_OF_MONTH, 1, 30 }, { 1700, 7, 23, DAY_OF_MONTH, 1, 31 }, { 1700, 8, 23, DAY_OF_MONTH, 1, 31 }, { 1700, 9, 23, DAY_OF_MONTH, 1, 30 }, { 1700, 10, 23, DAY_OF_MONTH, 1, 31 }, { 1700, 11, 23, DAY_OF_MONTH, 1, 30 }, { 1700, 12, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 1, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 2, 23, DAY_OF_MONTH, 1, 28 }, { 1751, 3, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 4, 23, DAY_OF_MONTH, 1, 30 }, { 1751, 5, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 6, 23, DAY_OF_MONTH, 1, 30 }, { 1751, 7, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 8, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 9, 23, DAY_OF_MONTH, 1, 30 }, { 1751, 10, 23, DAY_OF_MONTH, 1, 31 }, { 1751, 11, 23, DAY_OF_MONTH, 1, 30 }, { 1751, 12, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 1, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 2, 23, DAY_OF_MONTH, 1, 29 }, { 1752, 3, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 4, 23, DAY_OF_MONTH, 1, 30 }, { 1752, 5, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 6, 23, DAY_OF_MONTH, 1, 30 }, { 1752, 7, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 8, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 9, 23, DAY_OF_MONTH, 1, 30 }, { 1752, 10, 23, DAY_OF_MONTH, 1, 31 }, { 1752, 11, 23, DAY_OF_MONTH, 1, 30 }, { 1752, 12, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 1, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 2, 23, DAY_OF_MONTH, 1, 29 }, { 2012, 3, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 4, 23, DAY_OF_MONTH, 1, 30 }, { 2012, 5, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 6, 23, DAY_OF_MONTH, 1, 30 }, { 2012, 7, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 8, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 9, 23, DAY_OF_MONTH, 1, 30 }, { 2012, 10, 23, DAY_OF_MONTH, 1, 31 }, { 2012, 11, 23, DAY_OF_MONTH, 1, 30 }, { 2012, 12, 23, DAY_OF_MONTH, 1, 31 }, { 2011, 2, 23, DAY_OF_MONTH, 1, 28 }, { 1700, 1, 23, DAY_OF_YEAR, 1, 366 }, { 1751, 1, 23, DAY_OF_YEAR, 1, 365 }, { 1752, 1, 23, DAY_OF_YEAR, 1, 355 }, { 1753, 1, 23, DAY_OF_YEAR, 1, 365 }, { 2012, 1, 23, DAY_OF_YEAR, 1, 366 }, { 2011, 2, 23, DAY_OF_YEAR, 1, 365 }, { 1752, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 4, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 5, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 6, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 7, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 8, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 9, 23, ALIGNED_WEEK_OF_MONTH, 1, 3 }, { 1752, 10, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 11, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 1752, 12, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 2012, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 2011, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 }, { 1752, 12, 23, ALIGNED_WEEK_OF_YEAR, 1, 51 }, { 2011, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53 }, { 2012, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_getLong() {
        return new Object[][] { { 1752, 5, 26, DAY_OF_WEEK, 2 }, { 1752, 5, 26, DAY_OF_MONTH, 26 }, { 1752, 5, 26, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 26 }, { 1752, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 }, { 1752, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 }, { 1752, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7 }, { 1752, 5, 26, ALIGNED_WEEK_OF_YEAR, 21 }, { 1752, 5, 26, MONTH_OF_YEAR, 5 }, { 1752, 9, 2, DAY_OF_WEEK, 3 }, { 1752, 9, 2, DAY_OF_MONTH, 2 }, { 1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 2 }, { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2 }, { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 1 }, { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1 }, { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 36 }, { 1752, 9, 2, MONTH_OF_YEAR, 9 }, { 1752, 9, 14, DAY_OF_WEEK, 4 }, { 1752, 9, 14, DAY_OF_MONTH, 14 }, { 1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 3 }, { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3 }, { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 1 }, { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2 }, { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 36 }, { 1752, 9, 14, MONTH_OF_YEAR, 9 }, { 2014, 5, 26, DAY_OF_WEEK, 1 }, { 2014, 5, 26, DAY_OF_MONTH, 26 }, { 2014, 5, 26, DAY_OF_YEAR, 31 + 28 + 31 + 30 + 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21 }, { 2014, 5, 26, MONTH_OF_YEAR, 5 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1 }, { 2014, 5, 26, YEAR, 2014 }, { 2014, 5, 26, ERA, 1 }, { 1, 6, 8, ERA, 1 }, { 0, 6, 8, ERA, 0 }, { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 1 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_with() {
        return new Object[][] { { 1752, 9, 2, DAY_OF_WEEK, 1, 1752, 8, 31 }, { 1752, 9, 2, DAY_OF_WEEK, 4, 1752, 9, 14 }, { 1752, 9, 2, DAY_OF_MONTH, 1, 1752, 9, 1 }, // lenient
        { 1752, 9, 2, DAY_OF_MONTH, 3, 1752, 9, 14 }, // lenient
        { 1752, 9, 2, DAY_OF_MONTH, 13, 1752, 9, 24 }, { 1752, 9, 2, DAY_OF_MONTH, 14, 1752, 9, 14 }, { 1752, 9, 2, DAY_OF_MONTH, 30, 1752, 9, 30 }, { 1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 1, 1752, 9, 1 }, { 1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 3, 1752, 9, 14 }, // lenient
        { 1752, 9, 2, DAY_OF_YEAR, 356, 1753, 1, 1 }, // lenient
        { 1752, 9, 2, DAY_OF_YEAR, 366, 1753, 1, 11 }, { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 1752, 9, 1 }, { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 1752, 9, 14 }, { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 2, 1752, 9, 20 }, { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 3, 1752, 9, 27 }, // lenient
        { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 4, 1752, 10, 4 }, // lenient
        { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 5, 1752, 10, 11 }, { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 1752, 9, 14 }, { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 1752, 9, 15 }, { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 1, 1752, 1, 1 }, { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 35, 1752, 8, 26 }, { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 37, 1752, 9, 20 }, { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 51, 1752, 12, 27 }, // lenient
        { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 52, 1753, 1, 3 }, { 1752, 9, 2, MONTH_OF_YEAR, 8, 1752, 8, 2 }, { 1752, 9, 2, MONTH_OF_YEAR, 10, 1752, 10, 2 }, { 1752, 9, 14, DAY_OF_WEEK, 1, 1752, 8, 31 }, { 1752, 9, 14, DAY_OF_WEEK, 3, 1752, 9, 2 }, { 1752, 9, 14, DAY_OF_MONTH, 1, 1752, 9, 1 }, { 1752, 9, 14, DAY_OF_MONTH, 2, 1752, 9, 2 }, // lenient
        { 1752, 9, 14, DAY_OF_MONTH, 3, 1752, 9, 14 }, { 1752, 9, 14, DAY_OF_MONTH, 30, 1752, 9, 30 }, { 1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 1, 1752, 9, 1 }, { 1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 2, 1752, 9, 2 }, // lenient
        { 1752, 9, 14, DAY_OF_YEAR, 356, 1753, 1, 1 }, // lenient
        { 1752, 9, 14, DAY_OF_YEAR, 366, 1753, 1, 11 }, { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 1752, 9, 1 }, { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 1752, 9, 2 }, { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 2, 1752, 9, 21 }, { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 3, 1752, 9, 28 }, // lenient
        { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 4, 1752, 10, 5 }, // lenient
        { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 5, 1752, 10, 12 }, { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 1752, 9, 14 }, { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 1752, 9, 15 }, { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 1, 1752, 1, 2 }, { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 35, 1752, 8, 27 }, { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 37, 1752, 9, 21 }, { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 51, 1752, 12, 28 }, // lenient
        { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 52, 1753, 1, 4 }, { 1752, 9, 14, MONTH_OF_YEAR, 8, 1752, 8, 14 }, { 1752, 9, 14, MONTH_OF_YEAR, 10, 1752, 10, 14 }, // into cutover zone
        // lenient
        { 1752, 8, 4, MONTH_OF_YEAR, 9, 1752, 9, 15 }, // lenient
        { 1752, 10, 8, MONTH_OF_YEAR, 9, 1752, 9, 19 }, // lenient
        { 1751, 9, 4, YEAR, 1752, 1752, 9, 15 }, // lenient
        { 1753, 9, 8, YEAR, 1752, 1752, 9, 19 }, // lenient
        { 1751, 9, 4, YEAR_OF_ERA, 1752, 1752, 9, 15 }, // lenient
        { 1753, 9, 8, YEAR_OF_ERA, 1752, 1752, 9, 19 }, { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 28 }, { 2014, 5, 26, DAY_OF_WEEK, 7, 2014, 6, 1 }, { 2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31 }, { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 }, { 2014, 5, 26, DAY_OF_YEAR, 365, 2014, 12, 31 }, { 2014, 5, 26, DAY_OF_YEAR, 146, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 22 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 9 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21, 2014, 5, 26 }, { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 }, { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 }, { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 }, { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 }, { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 }, { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 }, { 2014, 5, 26, ERA, 0, -2013, 5, 26 }, { 2014, 5, 26, ERA, 1, 2014, 5, 26 }, { 2011, 3, 31, MONTH_OF_YEAR, 2, 2011, 2, 28 }, { 2012, 3, 31, MONTH_OF_YEAR, 2, 2012, 2, 29 }, { 2012, 3, 31, MONTH_OF_YEAR, 6, 2012, 6, 30 }, { 2012, 2, 29, YEAR, 2011, 2011, 2, 28 }, { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 }, { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 2, 2014, 5, 27 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_lastDayOfMonth() {
        return new Object[][] { { BritishCutoverDate.of(1752, 2, 23), BritishCutoverDate.of(1752, 2, 29) }, { BritishCutoverDate.of(1752, 6, 23), BritishCutoverDate.of(1752, 6, 30) }, { BritishCutoverDate.of(1752, 9, 2), BritishCutoverDate.of(1752, 9, 30) }, { BritishCutoverDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 30) }, { BritishCutoverDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 29) }, { BritishCutoverDate.of(2012, 6, 23), BritishCutoverDate.of(2012, 6, 30) } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_withLocalDate() {
        return new Object[][] { { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9, 1) }, { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 12), BritishCutoverDate.of(1752, 9, 1) }, { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14) }, { BritishCutoverDate.of(1752, 9, 15), LocalDate.of(1752, 9, 14), BritishCutoverDate.of(1752, 9, 14) }, { BritishCutoverDate.of(2012, 2, 23), LocalDate.of(2012, 2, 23), BritishCutoverDate.of(2012, 2, 23) } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        return new Object[][] { { 1752, 9, 2, -1, DAYS, 1752, 9, 1, true }, { 1752, 9, 2, 0, DAYS, 1752, 9, 2, true }, { 1752, 9, 2, 1, DAYS, 1752, 9, 14, true }, { 1752, 9, 2, 2, DAYS, 1752, 9, 15, true }, { 1752, 9, 14, -1, DAYS, 1752, 9, 2, true }, { 1752, 9, 14, 0, DAYS, 1752, 9, 14, true }, { 1752, 9, 14, 1, DAYS, 1752, 9, 15, true }, { 2014, 5, 26, 0, DAYS, 2014, 5, 26, true }, { 2014, 5, 26, 8, DAYS, 2014, 6, 3, true }, { 2014, 5, 26, -3, DAYS, 2014, 5, 23, true }, { 1752, 9, 2, -1, WEEKS, 1752, 8, 26, true }, { 1752, 9, 2, 0, WEEKS, 1752, 9, 2, true }, { 1752, 9, 2, 1, WEEKS, 1752, 9, 20, true }, { 1752, 9, 14, -1, WEEKS, 1752, 8, 27, true }, { 1752, 9, 14, 0, WEEKS, 1752, 9, 14, true }, { 1752, 9, 14, 1, WEEKS, 1752, 9, 21, true }, { 2014, 5, 26, 0, WEEKS, 2014, 5, 26, true }, { 2014, 5, 26, 3, WEEKS, 2014, 6, 16, true }, { 2014, 5, 26, -5, WEEKS, 2014, 4, 21, true }, { 1752, 9, 2, -1, MONTHS, 1752, 8, 2, true }, { 1752, 9, 2, 0, MONTHS, 1752, 9, 2, true }, { 1752, 9, 2, 1, MONTHS, 1752, 10, 2, true }, { 1752, 9, 14, -1, MONTHS, 1752, 8, 14, true }, { 1752, 9, 14, 0, MONTHS, 1752, 9, 14, true }, { 1752, 9, 14, 1, MONTHS, 1752, 10, 14, true }, { 1752, 8, 12, 1, MONTHS, 1752, 9, 23, false }, { 1752, 10, 12, -1, MONTHS, 1752, 9, 23, false }, { 2014, 5, 26, 0, MONTHS, 2014, 5, 26, true }, { 2014, 5, 26, 3, MONTHS, 2014, 8, 26, true }, { 2014, 5, 26, -5, MONTHS, 2013, 12, 26, true }, { 2014, 5, 26, 0, YEARS, 2014, 5, 26, true }, { 2014, 5, 26, 3, YEARS, 2017, 5, 26, true }, { 2014, 5, 26, -5, YEARS, 2009, 5, 26, true }, { 2014, 5, 26, 0, DECADES, 2014, 5, 26, true }, { 2014, 5, 26, 3, DECADES, 2044, 5, 26, true }, { 2014, 5, 26, -5, DECADES, 1964, 5, 26, true }, { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26, true }, { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26, true }, { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26, true }, { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26, true }, { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26, true }, { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26, true }, { 2014, 5, 26, -1, ERAS, -2013, 5, 26, true } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_until() {
        return new Object[][] { { 1752, 9, 1, 1752, 9, 2, DAYS, 1 }, { 1752, 9, 1, 1752, 9, 14, DAYS, 2 }, { 1752, 9, 2, 1752, 9, 14, DAYS, 1 }, { 1752, 9, 2, 1752, 9, 15, DAYS, 2 }, { 1752, 9, 14, 1752, 9, 1, DAYS, -2 }, { 1752, 9, 14, 1752, 9, 2, DAYS, -1 }, { 2014, 5, 26, 2014, 5, 26, DAYS, 0 }, { 2014, 5, 26, 2014, 6, 1, DAYS, 6 }, { 2014, 5, 26, 2014, 5, 20, DAYS, -6 }, { 1752, 9, 1, 1752, 9, 14, WEEKS, 0 }, { 1752, 9, 1, 1752, 9, 18, WEEKS, 0 }, { 1752, 9, 1, 1752, 9, 19, WEEKS, 1 }, { 1752, 9, 2, 1752, 9, 14, WEEKS, 0 }, { 1752, 9, 2, 1752, 9, 19, WEEKS, 0 }, { 1752, 9, 2, 1752, 9, 20, WEEKS, 1 }, { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 }, { 2014, 5, 26, 2014, 6, 1, WEEKS, 0 }, { 2014, 5, 26, 2014, 6, 2, WEEKS, 1 }, { 1752, 9, 1, 1752, 9, 14, MONTHS, 0 }, { 1752, 9, 1, 1752, 9, 30, MONTHS, 0 }, { 1752, 9, 1, 1752, 10, 1, MONTHS, 1 }, { 1752, 9, 2, 1752, 9, 14, MONTHS, 0 }, { 1752, 9, 2, 1752, 10, 1, MONTHS, 0 }, { 1752, 9, 2, 1752, 10, 2, MONTHS, 1 }, { 1752, 9, 14, 1752, 9, 15, MONTHS, 0 }, { 1752, 9, 14, 1752, 10, 13, MONTHS, 0 }, { 1752, 9, 14, 1752, 10, 14, MONTHS, 1 }, { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 }, { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 }, { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 }, { 2014, 5, 26, 2014, 5, 26, YEARS, 0 }, { 2014, 5, 26, 2015, 5, 25, YEARS, 0 }, { 2014, 5, 26, 2015, 5, 26, YEARS, 1 }, { 2014, 5, 26, 2014, 5, 26, DECADES, 0 }, { 2014, 5, 26, 2024, 5, 25, DECADES, 0 }, { 2014, 5, 26, 2024, 5, 26, DECADES, 1 }, { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 }, { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 }, { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 }, { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 }, { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 }, { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 }, { -2013, 5, 26, 0, 5, 26, ERAS, 0 }, { -2013, 5, 26, 2014, 5, 26, ERAS, 1 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_untilCLD() {
        return new Object[][] { { 1752, 7, 2, 1752, 7, 1, 0, 0, -1 }, { 1752, 7, 2, 1752, 7, 2, 0, 0, 0 }, // 30 days after 1752-08-02
        { 1752, 7, 2, 1752, 9, 1, 0, 1, 30 }, // 2 whole months
        { 1752, 7, 2, 1752, 9, 2, 0, 2, 0 }, // 1 day after 1752-09-02
        { 1752, 7, 2, 1752, 9, 14, 0, 2, 1 }, // 17 days after 1752-09-02
        { 1752, 7, 2, 1752, 9, 30, 0, 2, 17 }, // 18 days after 1752-09-02
        { 1752, 7, 2, 1752, 10, 1, 0, 2, 18 }, // 3 whole months
        { 1752, 7, 2, 1752, 10, 2, 0, 3, 0 }, { 1752, 7, 2, 1752, 10, 3, 0, 3, 1 }, { 1752, 7, 2, 1752, 10, 30, 0, 3, 28 }, { 1752, 7, 2, 1752, 11, 1, 0, 3, 30 }, { 1752, 7, 2, 1752, 11, 2, 0, 4, 0 }, // 30 days after 1752-08-03
        { 1752, 7, 3, 1752, 9, 2, 0, 1, 30 }, // 2 months
        { 1752, 7, 3, 1752, 9, 14, 0, 2, 0 }, // 1 day after 1752-09-03 (1752-09-14)
        { 1752, 7, 3, 1752, 9, 15, 0, 2, 1 }, // 16 days after 1752-09-03 (1752-09-14)
        { 1752, 7, 3, 1752, 9, 30, 0, 2, 16 }, // 17 days after 1752-09-03 (1752-09-14)
        { 1752, 7, 3, 1752, 10, 1, 0, 2, 17 }, { 1752, 7, 3, 1752, 10, 3, 0, 3, 0 }, { 1752, 7, 3, 1752, 10, 4, 0, 3, 1 }, // 29 days after 1752-08-04
        { 1752, 7, 4, 1752, 9, 2, 0, 1, 29 }, // 30 days after 1752-08-04
        { 1752, 7, 4, 1752, 9, 14, 0, 1, 30 }, // 2 months
        { 1752, 7, 4, 1752, 9, 15, 0, 2, 0 }, // 15 days after 1752-09-04 (1752-09-15)
        { 1752, 7, 4, 1752, 9, 30, 0, 2, 15 }, // 16 days after 1752-09-04 (1752-09-15)
        { 1752, 7, 4, 1752, 10, 1, 0, 2, 16 }, { 1752, 7, 4, 1752, 10, 4, 0, 3, 0 }, { 1752, 7, 4, 1752, 10, 5, 0, 3, 1 }, // 20 days after 1752-08-13
        { 1752, 7, 13, 1752, 9, 2, 0, 1, 20 }, // 21 days after 752-08-13
        { 1752, 7, 13, 1752, 9, 14, 0, 1, 21 }, // 2 months
        { 1752, 7, 13, 1752, 9, 24, 0, 2, 0 }, // 6 days after 1752-09-13 (1752-09-24)
        { 1752, 7, 13, 1752, 9, 30, 0, 2, 6 }, // 7 days after 1752-09-13 (1752-09-24)
        { 1752, 7, 13, 1752, 10, 1, 0, 2, 7 }, // 18 days after 1752-09-13 (1752-09-24)
        { 1752, 7, 13, 1752, 10, 12, 0, 2, 18 }, { 1752, 7, 13, 1752, 10, 13, 0, 3, 0 }, { 1752, 7, 13, 1752, 10, 14, 0, 3, 1 }, // 19 days after 1752-08-14
        { 1752, 7, 14, 1752, 9, 2, 0, 1, 19 }, // 2 months
        { 1752, 7, 14, 1752, 9, 14, 0, 2, 0 }, // 1 day after 1752-09-14
        { 1752, 7, 14, 1752, 9, 15, 0, 2, 1 }, // 16 days after 1752-09-14
        { 1752, 7, 14, 1752, 9, 30, 0, 2, 16 }, // 17 days after 1752-09-14
        { 1752, 7, 14, 1752, 10, 1, 0, 2, 17 }, // 29 days after 1752-09-14
        { 1752, 7, 14, 1752, 10, 13, 0, 2, 29 }, { 1752, 7, 14, 1752, 10, 14, 0, 3, 0 }, { 1752, 7, 14, 1752, 10, 15, 0, 3, 1 }, { 1752, 8, 2, 1752, 9, 2, 0, 1, 0 }, // 1 day after 1752-09-02
        { 1752, 8, 2, 1752, 9, 14, 0, 1, 1 }, // 17 days after 1752-09-02
        { 1752, 8, 2, 1752, 9, 30, 0, 1, 17 }, // 18 days after 1752-09-02
        { 1752, 8, 2, 1752, 10, 1, 0, 1, 18 }, { 1752, 8, 2, 1752, 10, 2, 0, 2, 0 }, { 1752, 8, 2, 1752, 10, 3, 0, 2, 1 }, { 1752, 8, 2, 1752, 10, 30, 0, 2, 28 }, { 1752, 8, 16, 1752, 9, 2, 0, 0, 17 }, { 1752, 8, 16, 1752, 9, 14, 0, 0, 18 }, { 1752, 8, 16, 1752, 9, 15, 0, 0, 19 }, { 1752, 8, 16, 1752, 9, 16, 0, 1, 0 }, { 1752, 8, 16, 1752, 10, 2, 0, 1, 16 }, { 1752, 8, 16, 1752, 10, 15, 0, 1, 29 }, { 1752, 8, 16, 1752, 10, 16, 0, 2, 0 }, { 1752, 8, 16, 1752, 10, 17, 0, 2, 1 }, { 1752, 9, 1, 1752, 8, 31, 0, 0, -1 }, { 1752, 9, 1, 1752, 9, 1, 0, 0, 0 }, { 1752, 9, 1, 1752, 9, 2, 0, 0, 1 }, { 1752, 9, 1, 1752, 9, 14, 0, 0, 2 }, { 1752, 9, 1, 1752, 9, 15, 0, 0, 3 }, { 1752, 9, 1, 1752, 9, 30, 0, 0, 18 }, { 1752, 9, 1, 1752, 10, 1, 0, 1, 0 }, { 1752, 9, 1, 1752, 10, 2, 0, 1, 1 }, { 1752, 9, 2, 1752, 8, 31, 0, 0, -2 }, { 1752, 9, 2, 1752, 9, 1, 0, 0, -1 }, { 1752, 9, 2, 1752, 9, 2, 0, 0, 0 }, { 1752, 9, 2, 1752, 9, 14, 0, 0, 1 }, { 1752, 9, 2, 1752, 9, 30, 0, 0, 17 }, { 1752, 9, 2, 1752, 10, 1, 0, 0, 18 }, { 1752, 9, 2, 1752, 10, 2, 0, 1, 0 }, { 1752, 9, 2, 1752, 10, 3, 0, 1, 1 }, { 1752, 9, 2, 1752, 11, 1, 0, 1, 30 }, { 1752, 9, 2, 1752, 11, 2, 0, 2, 0 }, { 1752, 9, 2, 1752, 11, 3, 0, 2, 1 }, { 1752, 9, 14, 1752, 7, 13, 0, -2, -1 }, { 1752, 9, 14, 1752, 7, 14, 0, -2, 0 }, { 1752, 9, 14, 1752, 8, 13, 0, -1, -1 }, { 1752, 9, 14, 1752, 8, 14, 0, -1, 0 }, // 19 days before
        { 1752, 9, 14, 1752, 8, 15, 0, 0, -19 }, // 3 days before
        { 1752, 9, 14, 1752, 8, 31, 0, 0, -3 }, // 2 days before
        { 1752, 9, 14, 1752, 9, 1, 0, 0, -2 }, // 1 day before
        { 1752, 9, 14, 1752, 9, 2, 0, 0, -1 }, { 1752, 9, 14, 1752, 9, 14, 0, 0, 0 }, { 1752, 9, 14, 1752, 9, 15, 0, 0, 1 }, { 1752, 9, 14, 1752, 9, 30, 0, 0, 16 }, { 1752, 9, 14, 1752, 10, 13, 0, 0, 29 }, { 1752, 9, 14, 1752, 10, 14, 0, 1, 0 }, { 1752, 9, 14, 1752, 10, 15, 0, 1, 1 }, { 1752, 9, 24, 1752, 7, 23, 0, -2, -1 }, { 1752, 9, 24, 1752, 7, 24, 0, -2, 0 }, { 1752, 9, 24, 1752, 8, 23, 0, -1, -1 }, { 1752, 9, 24, 1752, 8, 24, 0, -1, 0 }, // 19 days before
        { 1752, 9, 24, 1752, 8, 25, 0, 0, -19 }, // 13 days before
        { 1752, 9, 24, 1752, 8, 31, 0, 0, -13 }, // 12 days before
        { 1752, 9, 24, 1752, 9, 1, 0, 0, -12 }, // 11 days before
        { 1752, 9, 24, 1752, 9, 2, 0, 0, -11 }, // 10 days before
        { 1752, 9, 24, 1752, 9, 14, 0, 0, -10 }, // 1 day before
        { 1752, 9, 24, 1752, 9, 23, 0, 0, -1 }, { 1752, 9, 24, 1752, 9, 24, 0, 0, 0 }, { 1752, 9, 24, 1752, 9, 25, 0, 0, 1 }, { 1752, 9, 24, 1752, 9, 30, 0, 0, 6 }, { 1752, 9, 24, 1752, 10, 23, 0, 0, 29 }, { 1752, 9, 24, 1752, 10, 24, 0, 1, 0 }, { 1752, 9, 24, 1752, 10, 25, 0, 1, 1 }, { 1752, 10, 3, 1752, 10, 1, 0, 0, -2 }, { 1752, 10, 3, 1752, 9, 30, 0, 0, -3 }, { 1752, 10, 3, 1752, 9, 16, 0, 0, -17 }, { 1752, 10, 3, 1752, 9, 15, 0, 0, -18 }, { 1752, 10, 3, 1752, 9, 14, 0, 0, -19 }, { 1752, 10, 3, 1752, 9, 2, 0, -1, -1 }, { 1752, 10, 3, 1752, 9, 1, 0, -1, -2 }, { 1752, 10, 3, 1752, 8, 31, 0, -1, -3 }, { 1752, 10, 3, 1752, 8, 4, 0, -1, -30 }, { 1752, 10, 3, 1752, 8, 3, 0, -2, 0 }, { 1752, 10, 3, 1752, 8, 2, 0, -2, -1 }, { 1752, 10, 4, 1752, 10, 1, 0, 0, -3 }, { 1752, 10, 4, 1752, 9, 30, 0, 0, -4 }, { 1752, 10, 4, 1752, 9, 16, 0, 0, -18 }, { 1752, 10, 4, 1752, 9, 15, 0, 0, -19 }, { 1752, 10, 4, 1752, 9, 14, 0, 0, -20 }, { 1752, 10, 4, 1752, 9, 2, 0, -1, -2 }, { 1752, 10, 4, 1752, 9, 1, 0, -1, -3 }, { 1752, 10, 4, 1752, 8, 31, 0, -1, -4 }, { 1752, 10, 4, 1752, 8, 5, 0, -1, -30 }, { 1752, 10, 4, 1752, 8, 4, 0, -2, 0 }, { 1752, 10, 4, 1752, 8, 3, 0, -2, -1 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_toString() {
        return new Object[][] { { BritishCutoverDate.of(1, 1, 1), "BritishCutover AD 1-01-01" }, { BritishCutoverDate.of(2012, 6, 23), "BritishCutover AD 2012-06-23" } };
    }

    @ParameterizedTest
    @MethodSource("data_untilCLD")
    public void test_until_CLD(int year1, int month1, int dom1, int year2, int month2, int dom2, int expectedYears, int expectedMonths, int expectedDays) {
        BritishCutoverDate a = BritishCutoverDate.of(year1, month1, dom1);
        BritishCutoverDate b = BritishCutoverDate.of(year2, month2, dom2);
        ChronoPeriod c = a.until(b);
        assertEquals(BritishCutoverChronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays), c);
    }
}
