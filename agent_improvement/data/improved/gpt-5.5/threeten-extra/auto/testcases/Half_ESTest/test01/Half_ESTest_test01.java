package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test01 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Half secondHalfOfYear = Half.H2;
        MinguoDate nonIsoDate = MockMinguoDate.now();

        try {
            secondHalfOfYear.adjustInto(nonIsoDate);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Half.adjustInto only supports ISO date-time implementations.
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
