package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test09 extends Quarter_ESTest_scaffolding {

    /**
     * The second quarter (April to June) always has 91 days,
     * regardless of whether the year is a leap year.
     */
    @Test(timeout = 4000)
    public void q2LengthIsAlwaysNinetyOneDays() throws Throwable {
        Quarter secondQuarter = Quarter.Q2;

        int lengthInLeapYear = secondQuarter.length(true);

        assertEquals(91, lengthInLeapYear);
    }
}
