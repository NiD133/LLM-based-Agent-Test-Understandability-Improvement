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
import java.time.chrono.IsoEra;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.function.IntPredicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.testing.EqualsTester;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_eraOf_invalid {

    //-----------------------------------------------------------------------
    public static Object[][] data_samples() {
        return new Object[][] { { PaxDate.of(1, 1, 1), LocalDate.of(0, 12, 31) }, { PaxDate.of(1, 1, 2), LocalDate.of(1, 1, 1) }, { PaxDate.of(1, 1, 3), LocalDate.of(1, 1, 2) }, { PaxDate.of(1, 1, 28), LocalDate.of(1, 1, 27) }, { PaxDate.of(1, 2, 1), LocalDate.of(1, 1, 28) }, { PaxDate.of(1, 2, 2), LocalDate.of(1, 1, 29) }, { PaxDate.of(1, 2, 3), LocalDate.of(1, 1, 30) }, { PaxDate.of(6, 13, 6), LocalDate.of(6, 12, 1) }, { PaxDate.of(6, 13, 7), LocalDate.of(6, 12, 2) }, { PaxDate.of(6, 14, 1), LocalDate.of(6, 12, 3) }, { PaxDate.of(6, 14, 2), LocalDate.of(6, 12, 4) }, { PaxDate.of(6, 14, 3), LocalDate.of(6, 12, 5) }, { PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29) }, { PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30) }, { PaxDate.of(7, 1, 1), LocalDate.of(6, 12, 31) }, { PaxDate.of(7, 1, 2), LocalDate.of(7, 1, 1) }, { PaxDate.of(399, 13, 6), LocalDate.of(399, 12, 3) }, { PaxDate.of(399, 13, 7), LocalDate.of(399, 12, 4) }, { PaxDate.of(399, 14, 1), LocalDate.of(399, 12, 5) }, { PaxDate.of(399, 14, 2), LocalDate.of(399, 12, 6) }, { PaxDate.of(399, 14, 3), LocalDate.of(399, 12, 7) }, { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) }, { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) }, { PaxDate.of(401, 1, 1), LocalDate.of(400, 12, 31) }, { PaxDate.of(401, 1, 2), LocalDate.of(401, 1, 1) }, { PaxDate.of(401, 1, 3), LocalDate.of(401, 1, 2) }, { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) }, { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) }, { PaxDate.of(1582, 10, 5), LocalDate.of(1582, 9, 9) }, { PaxDate.of(1582, 10, 6), LocalDate.of(1582, 9, 10) }, { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10, 6) }, { PaxDate.of(2012, 6, 23), LocalDate.of(2012, 6, 4) }, { PaxDate.of(2012, 6, 24), LocalDate.of(2012, 6, 5) }, { PaxDate.of(-6, 1, 1), LocalDate.of(-6, 1, 2) }, { PaxDate.of(-6, 13, 6), LocalDate.of(-6, 12, 9) }, { PaxDate.of(-6, 13, 7), LocalDate.of(-6, 12, 10) }, { PaxDate.of(-6, 14, 1), LocalDate.of(-6, 12, 11) }, { PaxDate.of(-6, 14, 2), LocalDate.of(-6, 12, 12) }, { PaxDate.of(-6, 14, 27), LocalDate.of(-5, 1, 6) }, { PaxDate.of(-6, 14, 28), LocalDate.of(-5, 1, 7) }, { PaxDate.of(-5, 1, 1), LocalDate.of(-5, 1, 8) }, { PaxDate.of(-5, 1, 2), LocalDate.of(-5, 1, 9) }, { PaxDate.of(-99, 1, 1), LocalDate.of(-99, 1, 6) }, { PaxDate.of(-99, 13, 6), LocalDate.of(-99, 12, 13) }, { PaxDate.of(-99, 13, 7), LocalDate.of(-99, 12, 14) }, { PaxDate.of(-99, 14, 1), LocalDate.of(-99, 12, 15) }, { PaxDate.of(-99, 14, 2), LocalDate.of(-99, 12, 16) }, { PaxDate.of(-100, 1, 1), LocalDate.of(-101, 12, 31) }, { PaxDate.of(-100, 13, 6), LocalDate.of(-100, 12, 7) }, { PaxDate.of(-100, 13, 7), LocalDate.of(-100, 12, 8) }, { PaxDate.of(-100, 14, 1), LocalDate.of(-100, 12, 9) }, { PaxDate.of(-100, 14, 2), LocalDate.of(-100, 12, 10) } };
    }

    public static Object[][] data_badDates() {
        return new Object[][] { { 1900, 0, 0 }, { 1900, -1, 1 }, { 1900, 0, 1 }, { 1900, 15, 1 }, { 1900, 16, 1 }, { 1900, 1, -1 }, { 1900, 1, 0 }, { 1900, 1, 29 }, { 1900, 13, -1 }, { 1900, 13, 0 }, { 1900, 13, 8 }, { 1900, 14, -1 }, { 1900, 14, 0 }, { 1900, 14, 29 }, { 1900, 14, 30 }, { 1898, 13, -1 }, { 1898, 13, 0 }, { 1898, 14, 29 }, { 1898, 14, 30 }, { 1898, 14, 1 }, { 1898, 14, 2 }, { 1900, 14, -1 }, { 1900, 14, 0 }, { 1900, 14, 29 }, { 1900, 2, 29 }, { 1900, 3, 29 }, { 1900, 4, 29 }, { 1900, 5, 29 }, { 1900, 6, 29 }, { 1900, 7, 29 }, { 1900, 8, 29 }, { 1900, 9, 29 }, { 1900, 10, 29 }, { 1900, 11, 29 }, { 1900, 12, 29 } };
    }

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] { { 1900, 1, 28 }, { 1900, 2, 28 }, { 1900, 3, 28 }, { 1900, 4, 28 }, { 1900, 5, 28 }, { 1900, 6, 28 }, { 1900, 7, 28 }, { 1900, 8, 28 }, { 1900, 9, 28 }, { 1900, 10, 28 }, { 1900, 11, 28 }, { 1900, 12, 28 }, { 1900, 13, 7 }, { 1900, 14, 28 }, { 1901, 13, 28 }, { 1902, 13, 28 }, { 1903, 13, 28 }, { 1904, 13, 28 }, { 1905, 13, 28 }, { 1906, 13, 7 }, { 2000, 13, 28 }, { 2100, 13, 7 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_ranges() {
        return new Object[][] { { 2012, 1, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 2, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 3, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 4, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 5, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 6, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 7, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 8, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 9, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 10, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 11, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 12, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 13, 3, DAY_OF_MONTH, 1, 7 }, { 2012, 14, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 1, 23, MONTH_OF_YEAR, 1, 14 }, { 2012, 1, 23, DAY_OF_YEAR, 1, 371 }, { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 }, { 2012, 13, 3, ALIGNED_WEEK_OF_MONTH, 1, 1 }, { 2012, 14, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 }, { 2011, 13, 23, DAY_OF_MONTH, 1, 28 }, { 2011, 1, 23, MONTH_OF_YEAR, 1, 13 }, { 2011, 13, 23, DAY_OF_YEAR, 1, 364 }, { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_getLong() {
        return new Object[][] { { 2014, 5, 26, DAY_OF_WEEK, 4 }, { 2014, 5, 26, DAY_OF_MONTH, 26 }, { 2014, 5, 26, DAY_OF_YEAR, 28 + 28 + 28 + 28 + 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20 }, { 2014, 5, 26, MONTH_OF_YEAR, 5 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 20 * 18 - 5 + 2 + 5 - 1 }, { 2014, 5, 26, YEAR, 2014 }, { 2014, 5, 26, ERA, 1 }, { 1, 6, 8, ERA, 1 }, { 0, 6, 8, ERA, 0 }, { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 4 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_with() {
        return new Object[][] { { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 25 }, { 2014, 5, 26, DAY_OF_WEEK, 4, 2014, 5, 26 }, { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 }, { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 }, { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 13, 28 }, { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 }, { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 }, { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 3 - 1, 2013, 3, 26 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 5 - 1, 2013, 5, 26 }, { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 }, { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 }, { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 }, { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 }, { 2014, 5, 26, ERA, 0, -2013, 5, 26 }, { 2014, 5, 26, ERA, 1, 2014, 5, 26 }, { 2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28 }, { 2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 7 }, { 2012, 3, 28, MONTH_OF_YEAR, 6, 2012, 6, 28 }, { 2012, 13, 7, YEAR, 2011, 2011, 13, 7 }, { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 }, { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 25 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        return new Object[][] { { 2014, 5, 26, 0, DAYS, 2014, 5, 26 }, { 2014, 5, 26, 8, DAYS, 2014, 6, 6 }, { 2014, 5, 26, -3, DAYS, 2014, 5, 23 }, { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 }, { 2014, 5, 26, 3, WEEKS, 2014, 6, 19 }, { 2014, 5, 26, -5, WEEKS, 2014, 4, 19 }, { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 }, { 2014, 5, 26, 3, MONTHS, 2014, 8, 26 }, { 2014, 5, 26, -5, MONTHS, 2013, 13, 26 }, { 2014, 5, 26, 0, YEARS, 2014, 5, 26 }, { 2014, 5, 26, 3, YEARS, 2017, 5, 26 }, { 2014, 5, 26, -5, YEARS, 2009, 5, 26 }, { 2014, 5, 26, 0, DECADES, 2014, 5, 26 }, { 2014, 5, 26, 3, DECADES, 2044, 5, 26 }, { 2014, 5, 26, -5, DECADES, 1964, 5, 26 }, { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 }, { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26 }, { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 }, { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 }, { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26 }, { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 }, { 2014, 5, 26, -1, ERAS, -2013, 5, 26 }, { 2012, 13, 6, 3, MONTHS, 2013, 2, 6 }, { 2011, 13, 26, 1, YEARS, 2012, 14, 26 }, { 2014, 13, 26, -2, YEARS, 2012, 14, 26 }, { 2012, 14, 26, -6, YEARS, 2006, 14, 26 }, { 2012, 13, 6, -6, YEARS, 2006, 13, 6 }, { -2014, 5, 26, 0, MONTHS, -2014, 5, 26 }, { -2014, 5, 26, 3, MONTHS, -2014, 8, 26 }, { -2014, 5, 26, -5, MONTHS, -2015, 13, 26 } };
    }

    public static Object[][] data_plus_leap() {
        return new Object[][] { { 2012, 12, 26, 1, MONTHS, 2012, 13, 7 }, { 2012, 14, 26, -1, MONTHS, 2012, 13, 7 }, { 2012, 13, 6, 3, YEARS, 2015, 13, 6 } };
    }

    public static Object[][] data_minus_leap() {
        return new Object[][] { { 2012, 13, 7, -1, MONTHS, 2012, 12, 26 }, { 2012, 13, 7, 1, MONTHS, 2012, 14, 26 }, { 2012, 14, 6, 3, YEARS, 2015, 13, 6 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_until() {
        return new Object[][] { { 2014, 5, 26, 2014, 5, 26, DAYS, 0 }, { 2014, 5, 26, 2014, 6, 4, DAYS, 6 }, { 2014, 5, 26, 2014, 5, 20, DAYS, -6 }, { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 }, { 2014, 5, 26, 2014, 6, 4, WEEKS, 0 }, { 2014, 5, 26, 2014, 6, 5, WEEKS, 1 }, { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 }, { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 }, { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 }, { 2014, 5, 26, 2014, 5, 26, YEARS, 0 }, { 2014, 5, 26, 2015, 5, 25, YEARS, 0 }, { 2014, 5, 26, 2015, 5, 26, YEARS, 1 }, { 2014, 5, 26, 2014, 5, 26, DECADES, 0 }, { 2014, 5, 26, 2024, 5, 25, DECADES, 0 }, { 2014, 5, 26, 2024, 5, 26, DECADES, 1 }, { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 }, { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 }, { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 }, { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 }, { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 }, { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 }, { -2013, 5, 26, 0, 5, 26, ERAS, 0 }, { -2013, 5, 26, 2014, 5, 26, ERAS, 1 }, { 2011, 13, 26, 2013, 13, 26, YEARS, 2 }, { 2011, 13, 26, 2012, 14, 26, YEARS, 1 }, { 2012, 14, 26, 2011, 13, 26, YEARS, -1 }, { 2012, 14, 26, 2013, 13, 26, YEARS, 1 }, { 2011, 13, 6, 2012, 13, 6, YEARS, 0 }, { 2012, 13, 6, 2011, 13, 6, YEARS, 0 }, { 2011, 13, 1, 2012, 13, 7, YEARS, 0 }, { 2012, 13, 7, 2011, 13, 1, YEARS, 0 }, { 2011, 12, 28, 2012, 13, 1, YEARS, 1 }, { 2012, 13, 1, 2011, 12, 28, YEARS, -1 }, { 2013, 13, 6, 2012, 13, 6, YEARS, -1 }, { 2012, 13, 6, 2013, 13, 6, YEARS, 1 } };
    }

    public static Object[][] data_until_period() {
        return new Object[][] { { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 }, { 2014, 5, 26, 2014, 6, 4, 0, 0, 6 }, { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 }, { 2014, 5, 26, 2014, 6, 5, 0, 0, 7 }, { 2014, 5, 26, 2014, 6, 25, 0, 0, 27 }, { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 }, { 2014, 5, 26, 2015, 5, 25, 0, 12, 27 }, { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 }, { 2014, 5, 26, 2024, 5, 25, 9, 12, 27 }, { 2011, 13, 26, 2013, 13, 26, 2, 0, 0 }, { 2011, 13, 26, 2012, 14, 26, 1, 0, 0 }, { 2012, 14, 26, 2011, 13, 26, -1, 0, 0 }, { 2012, 14, 26, 2013, 13, 26, 1, 0, 0 }, { 2011, 13, 6, 2012, 13, 6, 0, 13, 0 }, { 2012, 13, 6, 2011, 13, 6, 0, -13, 0 }, { 2011, 13, 1, 2012, 13, 7, 0, 13, 6 }, { 2012, 13, 7, 2011, 13, 1, 0, -13, -6 }, { 2011, 12, 28, 2012, 13, 1, 1, 0, 1 }, { 2012, 13, 1, 2011, 12, 28, -1, 0, -1 }, { 2013, 13, 6, 2012, 13, 6, -1, -1, 0 }, { 2012, 13, 6, 2013, 13, 6, 1, 0, 0 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_toString() {
        return new Object[][] { { PaxDate.of(1, 1, 1), "Pax CE 1-01-01" }, { PaxDate.of(2012, 6, 23), "Pax CE 2012-06-23" } };
    }

    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> PaxChronology.INSTANCE.eraOf(2));
    }
}
