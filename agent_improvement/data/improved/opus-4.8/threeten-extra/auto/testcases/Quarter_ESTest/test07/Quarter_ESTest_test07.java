package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test07 extends Quarter_ESTest_scaffolding {

    /**
     * Q1 (January to March) spans 90 days in a standard (non-leap) year.
     */
    @Test(timeout = 4000)
    public void lengthOfQ1InStandardYearIs90Days() throws Throwable {
        Quarter firstQuarter = Quarter.Q1;

        int lengthInStandardYear = firstQuarter.length(false);

        assertEquals(90, lengthInStandardYear);
    }
}
