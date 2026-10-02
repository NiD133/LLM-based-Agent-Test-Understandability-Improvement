package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test20 extends DayOfYear_ESTest_scaffolding {

    /**
     * Comparing a DayOfYear to itself should report equality (a comparison
     * result of 0), since compareTo is based purely on the day value.
     *
     * The mocked clock fixes "today" to the 45th day of the year, so the
     * instance returned by now() has a value of 45.
     */
    @Test(timeout = 4000)
    public void comparingDayOfYearToItselfReturnsZero() throws Throwable {
        DayOfYear today = DayOfYear.now();

        int comparison = today.compareTo(today);

        assertEquals("now() should resolve to the 45th day of the mocked year",
                45, today.getValue());
        assertEquals("a DayOfYear compared to itself should be equal",
                0, comparison);
    }
}
