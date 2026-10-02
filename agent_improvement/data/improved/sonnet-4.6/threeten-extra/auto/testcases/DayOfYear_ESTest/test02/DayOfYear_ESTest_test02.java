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
public class DayOfYear_ESTest_test02 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that DayOfYear.now() returns day 45 under the mocked clock,
     * and that equals() is reflexive (an instance equals itself).
     */
    @Test(timeout = 4000)
    public void test_nowReturnsMockedDayAndEqualsIsReflexive() throws Throwable {
        DayOfYear today = DayOfYear.now();

        // The mocked clock fixes the current date to day-of-year 45
        assertEquals(45, today.getValue());

        // equals() must be reflexive: an object is always equal to itself
        boolean equalsItself = today.equals(today);
        assertTrue(equalsItself);
    }
}
