package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test19 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Days_ONE_isNotNegativeAndHasAmountOfOne() throws Throwable {
        Days oneDayConstant = Days.ONE;

        boolean isNegative = oneDayConstant.isNegative();
        assertFalse("Days.ONE should not be negative", isNegative);
        assertEquals("Days.ONE should have an amount of 1", 1, oneDayConstant.getAmount());
    }
}
