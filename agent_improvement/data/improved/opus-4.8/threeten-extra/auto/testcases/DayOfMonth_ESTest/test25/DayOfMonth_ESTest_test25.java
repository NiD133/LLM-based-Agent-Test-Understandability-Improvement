package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test25 extends DayOfMonth_ESTest_scaffolding {

    /**
     * A {@code DayOfMonth} only carries a day value, so it lacks the year and
     * month information that {@link java.time.YearMonth} requires. Converting one
     * into a {@code YearMonth} must therefore fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void cannotCreateYearMonthFromDayOfMonth() throws Throwable {
        DayOfMonth dayOfMonth = DayOfMonth.now();

        try {
            MockYearMonth.from(dayOfMonth);
            fail("Expected DateTimeException: a DayOfMonth has no year or month to build a YearMonth from");
        } catch (DateTimeException expected) {
            // Message reads: "Unable to obtain YearMonth from TemporalAccessor: ..."
            verifyException("java.time.YearMonth", expected);
        }
    }
}
