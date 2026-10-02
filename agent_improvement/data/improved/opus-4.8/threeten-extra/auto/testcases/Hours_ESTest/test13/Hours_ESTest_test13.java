package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test13 extends Hours_ESTest_scaffolding {

    /**
     * Adding zero hours to {@link Hours#ZERO} should leave the amount unchanged at zero.
     */
    @Test(timeout = 4000)
    public void plusZeroHoursToZeroKeepsAmountZero() throws Throwable {
        Hours result = Hours.ZERO.plus(0);

        assertEquals(0, result.getAmount());
    }
}
