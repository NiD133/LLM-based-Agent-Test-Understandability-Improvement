package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_until_end {

    public static Object[][] data_until_period() {
        return new Object[][] {
                { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
                { 2014, 5, 26, 2014, 6, 4, 0, 0, 6 },
                { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
                { 2014, 5, 26, 2014, 6, 5, 0, 0, 7 },
                { 2014, 5, 26, 2014, 6, 25, 0, 0, 27 },
                { 2014, 5, 26, 2014, 6, 26, 0, 1, 0 },
                { 2014, 5, 26, 2015, 5, 25, 0, 12, 27 },
                { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
                { 2014, 5, 26, 2024, 5, 25, 9, 12, 27 },

                { 2011, 13, 26, 2013, 13, 26, 2, 0, 0 },
                { 2011, 13, 26, 2012, 13, 26, 1, 0, 0 },
                { 2012, 13, 26, 2011, 13, 26, -1, 0, 0 },
                { 2012, 13, 26, 2013, 13, 26, 1, 0, 0 },
                { 2011, 13, 6, 2012, 13, 6, 1, 0, 0 },
                { 2012, 13, 6, 2011, 13, 6, -1, 0, 0 },
                { 2011, 13, 1, 2012, 13, 7, 1, 0, 6 },
                { 2012, 13, 7, 2011, 13, 1, -1, 0, -6 },
                { 2011, 12, 28, 2012, 13, 1, 1, 0, 1 },
                { 2012, 13, 1, 2011, 12, 28, -1, 0, -1 },
                { 2013, 13, 6, 2012, 13, 6, -1, 0, 0 },
                { 2012, 13, 6, 2013, 13, 6, 1, 0, 0 },

                // Start with Year Day.
                { 2012, 13, 29, 2012, 13, 29, 0, 0, 0 },
                { 2012, 13, 29, 2013, 13, 29, 1, 0, 0 },
                { 2011, 13, 29, 2010, 13, 29, -1, 0, 0 },
                { 2000, 13, 29, 2001, 13, 29, 1, 0, 0 },
                { 2007, 13, 29, 2008, 1, 1, 0, 0, 1 },
                { 2005, 13, 29, 2006, 2, 1, 0, 1, 1 },
                { 2003, 13, 29, 2004, 6, 29, 0, 6, 0 },
                { 2003, 13, 29, 2004, 7, 1, 0, 6, 1 },
                { 2004, 13, 29, 2004, 6, 29, 0, -7, 0 },
                { 2004, 13, 29, 2004, 7, 1, 0, -6, -27 },
                { 2003, 13, 29, 2005, 1, 1, 1, 0, 1 },
                { 2003, 13, 29, 2002, 13, 28, -1, 0, -1 },

                // Start with one day before Year Day.
                { 2003, 13, 28, 2004, 6, 29, 0, 6, 1 },
                { 2003, 13, 28, 2004, 7, 1, 0, 6, 2 },
                { 2004, 13, 28, 2004, 6, 29, 0, -7, 0 },
                { 2004, 13, 28, 2004, 7, 1, 0, -6, -27 },
                { 2003, 13, 28, 2005, 1, 1, 1, 0, 2 },
                { 2003, 13, 28, 2002, 13, 28, -1, 0, 0 },
                { 2007, 13, 28, 2008, 1, 1, 0, 0, 2 },
                { 2005, 13, 28, 2006, 2, 1, 0, 1, 1 },

                // Start with Leap Day.
                { 2008, 6, 29, 2008, 6, 29, 0, 0, 0 },
                { 2012, 6, 29, 2016, 6, 29, 4, 0, 0 },
                { 2024, 6, 29, 2020, 6, 29, -4, 0, 0 },
                { 2000, 6, 29, 2032, 6, 29, 32, 0, 0 },
                { 2024, 6, 29, 2000, 6, 29, -24, 0, 0 },
                { 2004, 6, 29, 2004, 13, 29, 0, 7, 0 },
                { 2004, 6, 29, 2004, 13, 28, 0, 6, 28 },
                { 2004, 6, 29, 2003, 13, 29, 0, -6, 0 },
                { 2004, 6, 29, 2003, 13, 28, 0, -6, -1 },
                { 2004, 6, 29, 2003, 6, 28, -1, 0, 0 },
                { 2000, 6, 29, 2000, 7, 1, 0, 0, 1 },
                { 2000, 6, 29, 2000, 8, 1, 0, 1, 1 },

                // Start with one day before Leap Day.
                { 2004, 6, 28, 2004, 13, 29, 0, 7, 1 },
                { 2004, 6, 28, 2004, 13, 28, 0, 7, 0 },
                { 2004, 6, 28, 2003, 13, 29, 0, -5, -28 },
                { 2004, 6, 28, 2003, 13, 28, 0, -6, 0 },
                { 2000, 6, 28, 2000, 7, 1, 0, 0, 2 },
                { 2000, 6, 28, 2000, 8, 1, 0, 1, 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            int expectedYears,
            int expectedMonths,
            int expectedDays) {

        InternationalFixedDate start = InternationalFixedDate.of(startYear, startMonth, startDayOfMonth);
        InternationalFixedDate end = InternationalFixedDate.of(endYear, endMonth, endDayOfMonth);
        ChronoPeriod period = InternationalFixedChronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(period, start.until(end));
    }
}
