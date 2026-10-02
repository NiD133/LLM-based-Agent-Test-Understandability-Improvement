package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_until_end {

    /**
     * Test data for {@link #test_until_end}: each row is
     * (year1, month1, dom1, year2, month2, dom2, expectedYears, expectedMonths, expectedDays).
     *
     * The expected period represents the result of {@code start.until(end)} expressed as
     * an IFC ChronoPeriod.
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // --- ordinary dates (no special Leap Day or Year Day involved) ---
            { 2014, 5, 26, 2014, 5, 26,  0,  0,  0 },  // same date → zero period
            { 2014, 5, 26, 2014, 6,  4,  0,  0,  6 },  // 6 days forward
            { 2014, 5, 26, 2014, 5, 20,  0,  0, -6 },  // 6 days backward
            { 2014, 5, 26, 2014, 6,  5,  0,  0,  7 },  // 7 days forward
            { 2014, 5, 26, 2014, 6, 25,  0,  0, 27 },  // 27 days forward (just under 1 month)
            { 2014, 5, 26, 2014, 6, 26,  0,  1,  0 },  // exactly 1 month forward
            { 2014, 5, 26, 2015, 5, 25,  0, 12, 27 },  // just under 1 year forward
            { 2014, 5, 26, 2015, 5, 26,  1,  0,  0 },  // exactly 1 year forward
            { 2014, 5, 26, 2024, 5, 25,  9, 12, 27 },  // just under 10 years forward

            // --- ordinary dates spanning years that include Year Day (month 13 / day 29) ---
            { 2011, 13, 26, 2013, 13, 26,  2,  0,  0 },
            { 2011, 13, 26, 2012, 13, 26,  1,  0,  0 },
            { 2012, 13, 26, 2011, 13, 26, -1,  0,  0 },
            { 2012, 13, 26, 2013, 13, 26,  1,  0,  0 },
            { 2011, 13,  6, 2012, 13,  6,  1,  0,  0 },
            { 2012, 13,  6, 2011, 13,  6, -1,  0,  0 },
            { 2011, 13,  1, 2012, 13,  7,  1,  0,  6 },
            { 2012, 13,  7, 2011, 13,  1, -1,  0, -6 },
            { 2011, 12, 28, 2012, 13,  1,  1,  0,  1 },
            { 2012, 13,  1, 2011, 12, 28, -1,  0, -1 },
            { 2013, 13,  6, 2012, 13,  6, -1,  0,  0 },
            { 2012, 13,  6, 2013, 13,  6,  1,  0,  0 },

            // --- start is Year Day (month 13, day 29) ---
            { 2012, 13, 29, 2012, 13, 29,  0,  0,  0 },   // same date
            { 2012, 13, 29, 2013, 13, 29,  1,  0,  0 },   // 1 year forward
            { 2011, 13, 29, 2010, 13, 29, -1,  0,  0 },   // 1 year backward
            { 2000, 13, 29, 2001, 13, 29,  1,  0,  0 },
            { 2007, 13, 29, 2008,  1,  1,  0,  0,  1 },   // 1 day to next year
            { 2005, 13, 29, 2006,  2,  1,  0,  1,  1 },   // 1 month + 1 day forward
            { 2003, 13, 29, 2004,  6, 29,  0,  6,  0 },   // 6 months to Leap Day
            { 2003, 13, 29, 2004,  7,  1,  0,  6,  1 },   // 6 months + 1 day
            { 2004, 13, 29, 2004,  6, 29,  0, -7,  0 },   // 7 months back to Leap Day
            { 2004, 13, 29, 2004,  7,  1,  0, -6, -27 },  // 6 months and -27 days back
            { 2003, 13, 29, 2005,  1,  1,  1,  0,  1 },   // 1 year + 1 day forward
            { 2003, 13, 29, 2002, 13, 28, -1,  0, -1 },   // 1 year + 1 day backward

            // --- start is one day before Year Day (month 13, day 28) ---
            { 2003, 13, 28, 2004,  6, 29,  0,  6,  1 },
            { 2003, 13, 28, 2004,  7,  1,  0,  6,  2 },
            { 2004, 13, 28, 2004,  6, 29,  0, -7,  0 },
            { 2004, 13, 28, 2004,  7,  1,  0, -6, -27 },
            { 2003, 13, 28, 2005,  1,  1,  1,  0,  2 },
            { 2003, 13, 28, 2002, 13, 28, -1,  0,  0 },
            { 2007, 13, 28, 2008,  1,  1,  0,  0,  2 },
            { 2005, 13, 28, 2006,  2,  1,  0,  1,  1 },

            // --- start is Leap Day (month 6, day 29 in a leap year) ---
            { 2008,  6, 29, 2008,  6, 29,  0,  0,  0 },   // same date
            { 2012,  6, 29, 2016,  6, 29,  4,  0,  0 },   // 4 years to next Leap Day
            { 2024,  6, 29, 2020,  6, 29, -4,  0,  0 },   // 4 years back
            { 2000,  6, 29, 2032,  6, 29, 32,  0,  0 },
            { 2024,  6, 29, 2000,  6, 29, -24,  0,  0 },
            { 2004,  6, 29, 2004, 13, 29,  0,  7,  0 },   // 7 months to Year Day
            { 2004,  6, 29, 2004, 13, 28,  0,  6, 28 },   // 6 months + 28 days (not 7 months flat)
            { 2004,  6, 29, 2003, 13, 29,  0, -6,  0 },   // 6 months back to prior Year Day
            { 2004,  6, 29, 2003, 13, 28,  0, -6, -1 },
            { 2004,  6, 29, 2003,  6, 28, -1,  0,  0 },
            { 2000,  6, 29, 2000,  7,  1,  0,  0,  1 },   // 1 day after Leap Day
            { 2000,  6, 29, 2000,  8,  1,  0,  1,  1 },   // 1 month + 1 day after Leap Day

            // --- start is one day before Leap Day (month 6, day 28) ---
            { 2004,  6, 28, 2004, 13, 29,  0,  7,  1 },
            { 2004,  6, 28, 2004, 13, 28,  0,  7,  0 },
            { 2004,  6, 28, 2003, 13, 29,  0, -5, -28 },  // -5 months and -28 days (not -6 months flat)
            { 2004,  6, 28, 2003, 13, 28,  0, -6,  0 },
            { 2000,  6, 28, 2000,  7,  1,  0,  0,  2 },
            { 2000,  6, 28, 2000,  8,  1,  0,  1,  1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int yearPeriod, int monthPeriod, int dayPeriod) {

        InternationalFixedDate start = InternationalFixedDate.of(year1, month1, dom1);
        InternationalFixedDate end   = InternationalFixedDate.of(year2, month2, dom2);
        ChronoPeriod expectedPeriod  = InternationalFixedChronology.INSTANCE.period(yearPeriod, monthPeriod, dayPeriod);

        assertEquals(expectedPeriod, start.until(end));
    }
}
