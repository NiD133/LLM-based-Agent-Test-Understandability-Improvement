package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.HijrahDate;
import java.time.chrono.MinguoDate;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.evosuite.runtime.mock.java.time.MockYear;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test09 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that isValidYear returns true for a non-leap day (day < 366),
     * regardless of whether the given year is a leap year.
     * The mocked clock fixes today as day 45 of the year.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Day 45 is always valid in any year because only day 366 requires a leap year
        DayOfYear today = DayOfYear.now();
        assertEquals("Mocked clock should yield day 45", 45, today.getValue());

        boolean validForNegativeYear = today.isValidYear(-1717);
        assertTrue("Day 45 should be valid in any year, including year -1717", validForNegativeYear);
    }
}
