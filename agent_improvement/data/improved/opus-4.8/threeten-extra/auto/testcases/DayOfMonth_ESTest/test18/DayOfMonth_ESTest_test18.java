package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test18 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that combining a DayOfMonth with a month via atMonth(Month)
     * leaves the original DayOfMonth unchanged (DayOfMonth is immutable).
     *
     * Under the EvoSuite mocked clock, DayOfMonth.now() resolves to day 14,
     * so the value must still be 14 after atMonth(...) is called.
     */
    @Test(timeout = 4000)
    public void atMonthDoesNotMutateOriginalDayOfMonth() throws Throwable {
        DayOfMonth currentDay = DayOfMonth.now();

        // Combine with January; the returned MonthDay is intentionally ignored
        // because we only care that the source DayOfMonth is not modified.
        currentDay.atMonth(Month.JANUARY);

        assertEquals(14, currentDay.getValue());
    }
}
