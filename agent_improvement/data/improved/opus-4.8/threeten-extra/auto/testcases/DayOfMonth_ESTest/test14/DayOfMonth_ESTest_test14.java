package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test14 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that a DayOfMonth obtained from the (mocked) system clock
     * supports the DAY_OF_MONTH field and exposes the expected day value.
     */
    @Test(timeout = 4000)
    public void nowSupportsDayOfMonthFieldAndHasExpectedValue() throws Throwable {
        DayOfMonth currentDayOfMonth = DayOfMonth.now();

        boolean supportsDayOfMonth = currentDayOfMonth.isSupported(ChronoField.DAY_OF_MONTH);

        assertTrue("DAY_OF_MONTH must be a supported field", supportsDayOfMonth);
        assertEquals("Mocked clock fixes the current day-of-month to 14", 14, currentDayOfMonth.getValue());
    }
}
