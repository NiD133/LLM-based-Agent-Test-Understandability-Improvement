package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test09 extends Days_ESTest_scaffolding {

    private static final int WEEKS = -3380;
    private static final int DAYS_PER_WEEK = 7;

    @Test(timeout = 4000)
    public void ofWeeksConvertsToDays_andAbsOfOneStaysOne() throws Throwable {
        // ofWeeks converts weeks into days by multiplying by 7.
        Days negativeWeeks = Days.ofWeeks(WEEKS);
        assertEquals(WEEKS * DAYS_PER_WEEK, negativeWeeks.getAmount());

        // abs() of the ONE constant (already positive) leaves the amount unchanged.
        Days absoluteOfOne = Days.ONE.abs();
        assertEquals(1, absoluteOfOne.getAmount());
    }
}
