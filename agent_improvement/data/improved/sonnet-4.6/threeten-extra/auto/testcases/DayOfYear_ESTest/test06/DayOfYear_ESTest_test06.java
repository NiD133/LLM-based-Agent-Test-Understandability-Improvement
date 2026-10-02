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
public class DayOfYear_ESTest_test06 extends DayOfYear_ESTest_scaffolding {

    // EvoSuite mocks the clock so that DayOfYear.now() returns day 45.
    private static final int MOCKED_CURRENT_DAY = 45;

    @Test(timeout = 4000)
    public void test_query_delegatesToProvidedTemporalQuery_andReturnsItsResult() throws Throwable {
        // Obtain the current DayOfYear (mocked to day 45 by the EvoSuite clock).
        DayOfYear currentDay = DayOfYear.now();

        // Create a TemporalQuery stub that returns currentDay for any TemporalAccessor argument.
        @SuppressWarnings("unchecked")
        TemporalQuery<Object> query = (TemporalQuery<Object>) mock(TemporalQuery.class, new ViolatedAssumptionAnswer());
        doReturn(currentDay).when(query).queryFrom(any(java.time.temporal.TemporalAccessor.class));

        // Verify that DayOfYear.query() delegates to the provided TemporalQuery and
        // returns the result produced by that query (the same DayOfYear instance).
        DayOfYear result = (DayOfYear) currentDay.query(query);
        assertEquals(MOCKED_CURRENT_DAY, result.getValue());
    }
}
