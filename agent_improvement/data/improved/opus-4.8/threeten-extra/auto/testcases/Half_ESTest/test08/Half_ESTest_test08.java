package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test08 extends Half_ESTest_scaffolding {

    /**
     * The second half-of-year (July to December) always spans 184 days,
     * regardless of whether the year is a leap year.
     */
    @Test(timeout = 4000)
    public void lengthOfSecondHalfInNonLeapYearIs184Days() throws Throwable {
        Half secondHalf = Half.H2;

        int lengthInDays = secondHalf.length(false);

        assertEquals(184, lengthInDays);
    }
}
