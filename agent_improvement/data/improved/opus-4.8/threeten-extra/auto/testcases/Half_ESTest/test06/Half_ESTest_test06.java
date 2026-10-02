package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test06 extends Half_ESTest_scaffolding {

    /**
     * The first half (January to June) spans 182 days in a leap year,
     * because February has 29 days instead of 28.
     */
    @Test(timeout = 4000)
    public void firstHalfHasOneExtraDayInLeapYear() throws Throwable {
        int leapYearLength = Half.H1.length(true);

        assertEquals(182, leapYearLength);
    }
}
