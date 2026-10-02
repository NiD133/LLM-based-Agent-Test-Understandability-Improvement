package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test09 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofWeeks_convertsToNegativeDays_andAbsOnOneReturnsOne() throws Throwable {
        // -3380 weeks * 7 days/week = -23660 days
        Days negativeWeeks = Days.ofWeeks(-3380);
        // Days.ONE is the constant 1-day instance; abs() of a positive value returns itself
        Days absOfOne = Days.ONE.abs();

        assertEquals(-23660, negativeWeeks.getAmount());
        assertEquals(1, absOfOne.getAmount());
    }
}
