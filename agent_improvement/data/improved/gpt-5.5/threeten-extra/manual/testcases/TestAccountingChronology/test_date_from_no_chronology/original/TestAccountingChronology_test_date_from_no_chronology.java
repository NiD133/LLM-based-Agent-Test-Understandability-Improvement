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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
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

public class TestAccountingChronology_test_date_from_no_chronology {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder().endsOn(DayOfWeek.SUNDAY).nearestEndOf(Month.AUGUST).withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS).leapWeekInMonth(13).toChronology();

    //-----------------------------------------------------------------------
    public static Object[][] data_samples() {
        return new Object[][] { { INSTANCE.date(1, 1, 1), LocalDate.of(0, 9, 4) }, { INSTANCE.date(1, 1, 2), LocalDate.of(0, 9, 5) }, { INSTANCE.date(1, 1, 3), LocalDate.of(0, 9, 6) }, { INSTANCE.date(2011, 13, 28), LocalDate.of(2011, 8, 28) }, { INSTANCE.date(2012, 1, 1), LocalDate.of(2011, 8, 29) }, { INSTANCE.date(2012, 1, 2), LocalDate.of(2011, 8, 30) }, { INSTANCE.date(2012, 1, 3), LocalDate.of(2011, 8, 31) }, { INSTANCE.date(2012, 13, 28), LocalDate.of(2012, 8, 26) }, { INSTANCE.date(2012, 13, 29), LocalDate.of(2012, 8, 27) }, { INSTANCE.date(2012, 13, 30), LocalDate.of(2012, 8, 28) }, { INSTANCE.date(2012, 13, 31), LocalDate.of(2012, 8, 29) }, { INSTANCE.date(2012, 13, 32), LocalDate.of(2012, 8, 30) }, { INSTANCE.date(2012, 13, 33), LocalDate.of(2012, 8, 31) }, { INSTANCE.date(2012, 13, 34), LocalDate.of(2012, 9, 1) }, { INSTANCE.date(2012, 13, 35), LocalDate.of(2012, 9, 2) }, { INSTANCE.date(2013, 1, 1), LocalDate.of(2012, 9, 3) }, { INSTANCE.date(2013, 1, 2), LocalDate.of(2012, 9, 4) }, { INSTANCE.date(2013, 1, 3), LocalDate.of(2012, 9, 5) }, { INSTANCE.date(0, 13, 35), LocalDate.of(0, 9, 3) }, { INSTANCE.date(0, 13, 34), LocalDate.of(0, 9, 2) }, { INSTANCE.date(1583, 2, 18), LocalDate.of(1582, 10, 14) }, { INSTANCE.date(1583, 2, 19), LocalDate.of(1582, 10, 15) }, { INSTANCE.date(1946, 3, 15), LocalDate.of(1945, 11, 12) }, { INSTANCE.date(2012, 12, 4), LocalDate.of(2012, 7, 5) }, { INSTANCE.date(2012, 12, 5), LocalDate.of(2012, 7, 6) } };
    }

    public static Object[][] data_badDates() {
        return new Object[][] { { 2012, 0, 0 }, { 2012, -1, 1 }, { 2012, 0, 1 }, { 2012, 14, 1 }, { 2012, 15, 1 }, { 2012, 1, -1 }, { 2012, 1, 0 }, { 2012, 1, 29 }, { 2012, 13, -1 }, { 2012, 13, 0 }, { 2012, 13, 36 }, { 2012, 13, 37 }, { 2012, 13, 38 }, { 2011, 13, -1 }, { 2011, 13, 0 }, { 2011, 13, 29 }, { 2011, 13, 30 }, { 2011, 13, 31 }, { 2011, 13, 32 }, { 2011, 13, 33 }, { 2011, 13, 34 }, { 2011, 13, 35 }, { 2012, 2, 29 }, { 2012, 3, 29 }, { 2012, 4, 29 }, { 2012, 5, 29 }, { 2012, 6, 29 }, { 2012, 7, 29 }, { 2012, 8, 29 }, { 2012, 9, 29 }, { 2012, 10, 29 }, { 2012, 11, 29 }, { 2012, 12, 29 } };
    }

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] { { 2012, 1, 28 }, { 2012, 2, 28 }, { 2012, 3, 28 }, { 2012, 4, 28 }, { 2012, 5, 28 }, { 2012, 6, 28 }, { 2012, 7, 28 }, { 2012, 8, 28 }, { 2012, 9, 28 }, { 2012, 10, 28 }, { 2012, 11, 28 }, { 2012, 12, 28 }, { 2012, 13, 35 }, { 2013, 13, 28 }, { 2014, 13, 28 }, { 2015, 13, 28 }, { 2016, 13, 28 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_ranges() {
        return new Object[][] { { 2012, 1, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 2, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 3, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 4, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 5, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 6, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 7, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 8, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 9, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 10, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 11, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 12, 23, DAY_OF_MONTH, 1, 28 }, { 2012, 13, 23, DAY_OF_MONTH, 1, 35 }, { 2012, 1, 23, DAY_OF_YEAR, 1, 371 }, { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 }, { 2012, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 }, { 2013, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 }, { 2011, 13, 23, DAY_OF_MONTH, 1, 28 }, { 2011, 13, 23, DAY_OF_YEAR, 1, 364 }, { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_getLong() {
        return new Object[][] { { 2014, 5, 26, DAY_OF_WEEK, 5 }, { 2014, 5, 26, DAY_OF_MONTH, 26 }, { 2014, 5, 26, DAY_OF_YEAR, 28 + 28 + 28 + 28 + 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20 }, { 2014, 5, 26, MONTH_OF_YEAR, 5 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 5 - 1 }, { 2014, 5, 26, YEAR, 2014 }, { 2014, 5, 26, ERA, 1 }, { 1, 6, 8, ERA, 1 }, { 0, 6, 8, ERA, 0 }, { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 5 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_with() {
        return new Object[][] { { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 24 }, { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 26 }, { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 }, { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 }, { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 13, 28 }, { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 }, { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24 }, { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 }, { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 }, { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 }, { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 3 - 1, 2013, 3, 26 }, { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 5 - 1, 2014, 5, 26 }, { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 }, { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 }, { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 }, { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 }, { 2014, 5, 26, ERA, 0, -2013, 5, 26 }, { 2014, 5, 26, ERA, 1, 2014, 5, 26 }, { 2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28 }, { 2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 28 }, { 2012, 13, 35, MONTH_OF_YEAR, 6, 2012, 6, 28 }, { 2012, 13, 35, YEAR, 2011, 2011, 13, 28 }, { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 }, { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 24 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_plus() {
        return new Object[][] { { 2014, 5, 26, 0, DAYS, 2014, 5, 26 }, { 2014, 5, 26, 8, DAYS, 2014, 6, 6 }, { 2014, 5, 26, -3, DAYS, 2014, 5, 23 }, { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 }, { 2014, 5, 26, 3, WEEKS, 2014, 6, 19 }, { 2014, 5, 26, -5, WEEKS, 2014, 4, 19 }, { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 }, { 2014, 5, 26, 3, MONTHS, 2014, 8, 26 }, { 2014, 5, 26, -5, MONTHS, 2013, 13, 26 }, { 2014, 5, 26, 0, YEARS, 2014, 5, 26 }, { 2014, 5, 26, 3, YEARS, 2017, 5, 26 }, { 2014, 5, 26, -5, YEARS, 2009, 5, 26 }, { 2014, 5, 26, 0, DECADES, 2014, 5, 26 }, { 2014, 5, 26, 3, DECADES, 2044, 5, 26 }, { 2014, 5, 26, -5, DECADES, 1964, 5, 26 }, { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 }, { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26 }, { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 }, { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 }, { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26 }, { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 }, { 2014, 5, 26, -1, ERAS, -2013, 5, 26 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_until() {
        return new Object[][] { { 2014, 5, 26, 2014, 5, 26, DAYS, 0 }, { 2014, 5, 26, 2014, 6, 4, DAYS, 6 }, { 2014, 5, 26, 2014, 5, 20, DAYS, -6 }, { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 }, { 2014, 5, 26, 2014, 6, 4, WEEKS, 0 }, { 2014, 5, 26, 2014, 6, 5, WEEKS, 1 }, { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 }, { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 }, { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 }, { 2014, 5, 26, 2014, 5, 26, YEARS, 0 }, { 2014, 5, 26, 2015, 5, 25, YEARS, 0 }, { 2014, 5, 26, 2015, 5, 26, YEARS, 1 }, { 2014, 5, 26, 2014, 5, 26, DECADES, 0 }, { 2014, 5, 26, 2024, 5, 25, DECADES, 0 }, { 2014, 5, 26, 2024, 5, 26, DECADES, 1 }, { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 }, { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 }, { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 }, { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 }, { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 }, { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 }, { -2013, 5, 26, 0, 5, 26, ERAS, 0 }, { -2013, 5, 26, 2014, 5, 26, ERAS, 1 } };
    }

    //-----------------------------------------------------------------------
    public static Object[][] data_toString() {
        AccountingChronology other = new AccountingChronologyBuilder().endsOn(DayOfWeek.SUNDAY).nearestEndOf(Month.AUGUST).withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS).leapWeekInMonth(13).accountingYearStartsInIsoYear().toChronology();
        return new Object[][] { { INSTANCE.date(1, 1, 1), "Accounting calendar ends on SUNDAY nearest end of AUGUST, year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 ending in the given ISO year CE 1-01-01" }, { INSTANCE.date(2012, 6, 23), "Accounting calendar ends on SUNDAY nearest end of AUGUST, year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 ending in the given ISO year CE 2012-06-23" }, { other.date(1, 1, 1), "Accounting calendar ends on SUNDAY nearest end of AUGUST, year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 starting in the given ISO year CE 1-01-01" }, { other.date(2012, 6, 23), "Accounting calendar ends on SUNDAY nearest end of AUGUST, year divided in THIRTEEN_EVEN_MONTHS_OF_4_WEEKS with leap-week in month 13 starting in the given ISO year CE 2012-06-23" } };
    }

    @Test
    public void test_date_from_no_chronology() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> AccountingDate.from(null, LocalDate.of(2012, 1, 1)));
    }
}
