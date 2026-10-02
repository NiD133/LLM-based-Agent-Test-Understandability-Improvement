package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test14 extends Weeks_ESTest_scaffolding {

    // Weeks.ONE is the singleton constant representing exactly one week;
    // it must report an amount of 1 and be considered positive.
    @Test(timeout = 4000)
    public void test_weeksOneConstant_hasAmountOneAndIsPositive() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        boolean isPositive = oneWeek.isPositive();

        assertEquals(1, oneWeek.getAmount());
        assertTrue(isPositive);
    }
}
