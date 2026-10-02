package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test21 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that combining a day-of-month with an out-of-range month number
     * fails. The {@code atMonth(int)} overload delegates to {@link java.time.Month#of(int)},
     * which only accepts month values 1..12, so the value -2313 triggers a
     * {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void atMonth_withInvalidMonthNumber_throwsDateTimeException() throws Throwable {
        DayOfMonth dayOfMonth = DayOfMonth.now();

        try {
            dayOfMonth.atMonth(-2313);
            fail("Expected a DateTimeException for the invalid month value -2313");
        } catch (DateTimeException expected) {
            // Thrown by java.time.Month.of: "Invalid value for MonthOfYear: -2313"
            verifyException("java.time.Month", expected);
        }
    }
}
