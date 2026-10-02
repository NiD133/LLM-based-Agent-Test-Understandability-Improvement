package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test02 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Two DayOfMonth instances with different day values are not equal, and the
     * inequality holds in both directions. Under the mocked system clock the
     * current day-of-month resolves to the 14th, which differs from the 1st.
     */
    @Test(timeout = 4000)
    public void differentDaysAreNotEqual() throws Throwable {
        DayOfMonth currentDay = DayOfMonth.now();
        DayOfMonth firstDay = DayOfMonth.of(1);

        assertEquals(14, currentDay.getValue());
        assertFalse("Day 14 should not equal day 1", currentDay.equals(firstDay));
        assertFalse("Day 1 should not equal day 14", firstDay.equals((Object) currentDay));
    }
}
